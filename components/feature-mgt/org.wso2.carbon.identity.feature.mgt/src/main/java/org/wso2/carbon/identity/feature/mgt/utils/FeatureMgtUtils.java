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

package org.wso2.carbon.identity.feature.mgt.utils;

import org.apache.commons.lang.StringUtils;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.identity.feature.mgt.FeatureMgtConstants;
import org.wso2.carbon.identity.feature.mgt.exception.FeatureManagementServerException;

/**
 * This class is used to define the Utilities required in feature management feature.
 */
public class FeatureMgtUtils {

    /**
     * Get the Tenant Domain from carbon context.
     *
     * @return Tenant Id.
     */
    public static int getTenantIDFromCarbonContext() {

        return PrivilegedCarbonContext.getThreadLocalCarbonContext().getTenantId();
    }

    /**
     * Used to generate a FeatureManagementServerException from IdentityFeatureMgtConstants
     * .ErrorMessages.object when no exception is thrown.
     *
     * @param error IdentityFeatureMgtConstants.ErrorMessages.
     * @param data  Data to replace if the message needs to be replaced.
     * @return FeatureManagementServerException.
     */
    public static FeatureManagementServerException handleServerException(FeatureMgtConstants.ErrorMessages error,
                                                                         String data, Throwable e) {

        String message;
        if (StringUtils.isNotBlank(data)) {
            message = String.format(error.getMessage(), data);
        } else {
            message = error.getMessage();
        }
        return new FeatureManagementServerException(message, error.getCode(), e);
    }
}
