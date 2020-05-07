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

import static org.wso2.carbon.identity.feature.mgt.utils.FeatureMgtUtils.getTenantDomainFromCarbonContext;

/**
 * Feature manager service implementation.
 */
public class FeatureManagerImpl implements FeatureManager {

    /**
     * Return the feature info given the feature id.
     *
     * @param featureId Unique identifier of the feature.
     * @return {@link Feature}.
     */
    @Override
    public Feature getFeatureById(String featureId) {

        return fetchFeatureById(featureId);
    }

    /**
     * Update a feature given the feature id by replacing the existing feature object.
     *
     * @param featureId Unique identifier of the the template.
     * @param feature   Updated feature object.
     */
    @Override
    public void updateFeatureById(String featureId, Feature feature) {

        FeatureManagerDAO featureManagerDAO = new FeatureManagerDAOImpl();
        featureManagerDAO.updateFeatureById(featureId, getTenantDomainFromCarbonContext(), feature);
    }

    /**
     * Delete a feature given the feature id.
     *
     * @param featureId Unique identifier of the feature.
     */
    @Override
    public void deleteFeatureById(String featureId) {

        FeatureManagerDAO featureManagerDAO = new FeatureManagerDAOImpl();
        featureManagerDAO.deleteFeatureById(featureId, getTenantDomainFromCarbonContext());
    }

    /**
     * Checks the status of the feature. Whether the feature is locked or unlocked.
     *
     * @param featureId Unique identifier of the the feature.
     * @return The status of the feature.
     */
    @Override
    public boolean isFeatureLocked(String featureId) {

        Feature feature = fetchFeatureById(featureId);
        return feature.isFeatureLocked();
    }

    /**
     * Get the reason/s for locking a feature given the feature id.
     *
     * @param featureId Unique identifier of the the feature.
     * @return The feature lock reason.
     */
    @Override
    public String[] getFeatureLockReason(String featureId) {

        Feature feature = fetchFeatureById(featureId);
        return feature.getFeatureLockReason();
    }

    /**
     * Lock a feature given the feature id and the feature lock reason/s.
     *
     * @param featureId             Unique identifier of the feature.
     * @param featureLockReasonCode The reason/s for locking the feature.
     */
    @Override
    public void lockFeature(String featureId, String[] featureLockReasonCode) {

        Feature feature = fetchFeatureById(featureId);
        feature.setFeatureLocked(Boolean.TRUE);
        feature.setFeatureLockReasonCode(featureLockReasonCode);

        FeatureManager featureManager = new FeatureManagerImpl();
        featureManager.updateFeatureById(featureId, feature);
    }

    private Feature fetchFeatureById(String featureId) {

        FeatureManagerDAO featureManagerDAO = new FeatureManagerDAOImpl();
        return featureManagerDAO.getFeatureById(featureId, getTenantDomainFromCarbonContext());
    }
}
