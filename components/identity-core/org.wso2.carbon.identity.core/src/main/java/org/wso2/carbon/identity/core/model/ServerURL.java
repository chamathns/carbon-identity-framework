/*
 * Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.identity.core.model;

import org.apache.axis2.engine.AxisConfiguration;
import org.apache.commons.lang.StringUtils;
import org.wso2.carbon.base.ServerConfiguration;
import org.wso2.carbon.identity.core.URLResolverException;
import org.wso2.carbon.identity.core.internal.IdentityCoreServiceComponent;
import org.wso2.carbon.identity.core.util.IdentityCoreConstants;
import org.wso2.carbon.utils.CarbonUtils;
import org.wso2.carbon.utils.NetworkUtils;

import java.net.SocketException;
import java.util.ArrayList;

/**
 * Server URL representation.
 */
public class ServerURL {

    private String protocol;
    private String hostName;
    private Integer port;
    private String proxyContext;
    private String webContext;
    private Boolean addProxyContext;
    private Boolean addWebContext;
    private String urlContext;
    private Boolean appendTenantAsQueryParam;
    private Boolean appendTenantAsPathParam;
    private ArrayList<String> parameters;
    private ArrayList<String> fragments;

    private ServerURL(ServerURLBuilder serverURLBuilder) {

        this.protocol = serverURLBuilder.protocol;
        this.hostName = serverURLBuilder.hostName;
        this.port = serverURLBuilder.port;
        this.proxyContext = serverURLBuilder.proxyContext;
        this.webContext = serverURLBuilder.webContext;
        this.addProxyContext = serverURLBuilder.addProxyContext;
        this.addWebContext = serverURLBuilder.addWebContext;
        this.urlContext = serverURLBuilder.urlContext;
        this.appendTenantAsQueryParam = serverURLBuilder.appendTenantAsQueryParam;
        this.appendTenantAsPathParam = serverURLBuilder.appendTenantAsPathParam;
        this.parameters = serverURLBuilder.parameters;
        this.fragments = serverURLBuilder.fragments;
    }

    public String getProtocol() {

        return protocol;
    }

    public String getHostName() {

        return hostName;
    }

    public Integer getPort() {

        return port;
    }

    public String getProxyContext() {

        return proxyContext;
    }

    public String getWebContext() {

        return webContext;
    }

    public String getUrlContext() {

        return urlContext;
    }

    public Boolean getAddProxyContext() {

        return addProxyContext;
    }

    public Boolean getAddWebContext() {

        return addWebContext;
    }

    public Boolean getAppendTenantAsQueryParam() {

        return appendTenantAsQueryParam;
    }

    public Boolean getAppendTenantAsPathParam() {

        return appendTenantAsPathParam;
    }

    public ArrayList<String> getParameters() {

        return parameters;
    }

    public ArrayList<String> getFragments() {

        return fragments;
    }

    private String fetchProtocol() {

        return CarbonUtils.getManagementTransport();
    }

    private String fetchHostName() throws URLResolverException {

        String hostName = ServerConfiguration.getInstance().getFirstProperty(IdentityCoreConstants.HOST_NAME);
        try {
            if (StringUtils.isBlank(hostName)) {
                hostName = NetworkUtils.getLocalHostname();
            }
        } catch (SocketException e) {
            throw new URLResolverException("Error while trying to resolve the hostname from the system.", e);
        }
        return hostName;
    }

    private Integer fetchPort() {

        String mgtTransport = CarbonUtils.getManagementTransport();
        AxisConfiguration axisConfiguration = IdentityCoreServiceComponent.getConfigurationContextService().
                getServerConfigContext().getAxisConfiguration();
        int port = CarbonUtils.getTransportProxyPort(axisConfiguration, mgtTransport);
        if (port <= 0) {
            port = CarbonUtils.getTransportPort(axisConfiguration, mgtTransport);
        }
        return port;
    }

    private String fetchProxyContextPath() {

        return ServerConfiguration.getInstance().getFirstProperty(IdentityCoreConstants.PROXY_CONTEXT_PATH);
    }

    private String fetchWebContextRoot() {

        return ServerConfiguration.getInstance().getFirstProperty(IdentityCoreConstants.WEB_CONTEXT_ROOT);
    }

    public String getAbsoluteURL() throws URLResolverException {

        StringBuilder absoluteURL = new StringBuilder();
        String protocol = this.getProtocol();
        if (StringUtils.isBlank(protocol)) {
            protocol = this.fetchProtocol();
        }
        absoluteURL.append(protocol).append("://");
        String hostName = this.getHostName();
        if (StringUtils.isBlank(hostName)) {
            hostName = this.fetchHostName();
        }
        if (hostName.endsWith("/")) {
            hostName = hostName.substring(0, hostName.length() - 1);
        }
        absoluteURL.append(hostName.toLowerCase());

        Integer port = this.getPort();
        if (port == null) {
            port = this.fetchPort();
        }
        if (port != IdentityCoreConstants.DEFAULT_HTTPS_PORT) {
            absoluteURL.append(":").append(port);
        }
        appendContext(absoluteURL);
        return absoluteURL.toString();
    }

    private void appendContext(StringBuilder serverURL) {

        if (this.getAddProxyContext()) {
            String proxyContextPath = this.getProxyContext();
            if (StringUtils.isBlank(proxyContextPath)) {
                proxyContextPath = fetchProxyContextPath();
            }
            if (StringUtils.isNotBlank(proxyContextPath)) {
                if (proxyContextPath.trim().charAt(0) != '/') {
                    serverURL.append("/").append(proxyContextPath.trim());
                } else {
                    serverURL.append(proxyContextPath.trim());
                }
            }
        }

        if (this.getAddWebContext()) {
            String webContextRoot = this.getWebContext();
            if (StringUtils.isBlank(webContextRoot)) {
                webContextRoot = fetchWebContextRoot();
            }
            if (StringUtils.isNotBlank(webContextRoot)) {
                if (webContextRoot.trim().charAt(0) != '/') {
                    serverURL.append("/").append(webContextRoot.trim());
                } else {
                    serverURL.append(webContextRoot.trim());
                }
            }
        }

    }

    /**
     * Server URL builder representation.
     */
    public static class ServerURLBuilder {

        private String protocol;
        private String hostName;
        private Integer port;
        private String proxyContext;
        private String webContext;
        private Boolean addProxyContext;
        private Boolean addWebContext;
        private String urlContext;
        private Boolean appendTenantAsQueryParam;
        private Boolean appendTenantAsPathParam;
        private ArrayList<String> parameters;
        private ArrayList<String> fragments;

        public ServerURLBuilder(String urlContext) {

            this.urlContext = urlContext;
        }

        public ServerURLBuilder protocol(String protocol) {

            this.protocol = protocol;
            return this;
        }

        public ServerURLBuilder hostName(String hostName) {

            this.hostName = hostName;
            return this;
        }

        public ServerURLBuilder port(Integer port) {

            this.port = port;
            return this;
        }

        public ServerURLBuilder proxyContext(String proxyContext) {

            this.proxyContext = proxyContext;
            return this;
        }

        public ServerURLBuilder webContext(String webContext) {

            this.webContext = webContext;
            return this;
        }

        public ServerURLBuilder addProxyContext() {

            this.addProxyContext = true;
            return this;
        }

        public ServerURLBuilder addWebContext() {

            this.addWebContext = true;
            return this;
        }


        public ServerURLBuilder appendTenantAsQueryParam() {

            this.appendTenantAsQueryParam = true;
            return this;
        }

        public ServerURLBuilder appendTenantAsPathParam() {

            this.appendTenantAsPathParam = true;
            return this;
        }

        public ServerURLBuilder parameters(ArrayList<String> parameters) {

            this.parameters = parameters;
            return this;
        }

        public ServerURLBuilder fragments(ArrayList<String> fragments) {

            this.fragments = fragments;
            return this;
        }

        public ServerURL build() {

            return new ServerURL(this);
        }
    }

}
