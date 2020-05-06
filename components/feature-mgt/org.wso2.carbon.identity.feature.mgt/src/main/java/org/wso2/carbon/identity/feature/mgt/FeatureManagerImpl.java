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

package org.wso2.carbon.identity.feature.mgt;

import org.wso2.carbon.identity.feature.mgt.dao.FeatureManagerDAO;
import org.wso2.carbon.identity.feature.mgt.dao.FeatureManagerDAOImpl;
import org.wso2.carbon.identity.feature.mgt.model.Feature;
import org.wso2.carbon.context.PrivilegedCarbonContext;

public class FeatureManagerImpl implements FeatureManager {

    /**
     * Return the feature info given the feature id.
     *
     * @param featureId unique identifier of the feature.
     * @return {@link Feature}.
     */
    @Override
    public Feature getFeatureById(String featureId) {

        FeatureManagerDAO featureManagerDAO = new FeatureManagerDAOImpl();
        String tenantDomain = getTenantDomain();

        return featureManagerDAO.getFeatureById(featureId, tenantDomain);
    }

    /**
     * Update a feature given the feature id by replacing the existing feature object.
     *
     * @param featureId unique identifier of the the template.
     * @param feature   updated feature object.
     */
    @Override
    public void updateFeatureById(String featureId, Feature feature) {

        FeatureManagerDAO featureManagerDAO = new FeatureManagerDAOImpl();
        String tenantDomain = getTenantDomain();

        featureManagerDAO.updateFeatureById(featureId, tenantDomain, feature);
    }

    /**
     * Delete a feature given the feature id.
     *
     * @param featureId unique identifier of the feature.
     */
    @Override
    public void deleteFeatureById(String featureId) {

        FeatureManagerDAO featureManagerDAO = new FeatureManagerDAOImpl();
        String tenantDomain = getTenantDomain();

        featureManagerDAO.deleteFeatureById(featureId, tenantDomain);
    }

    private String getTenantDomain() {

        return PrivilegedCarbonContext.getThreadLocalCarbonContext().getTenantDomain();
    }
}
