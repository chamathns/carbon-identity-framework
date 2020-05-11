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
     * Return the feature info given the feature id and the user id.
     *
     * @param featureId Unique identifier of the feature.
     * @param userId    Unique identifier of the user.
     * @return {@link Feature}.
     */
    @Override
    public Feature getFeatureById(String featureId, String userId) {

        return fetchFeatureById(featureId, userId);
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
     * Delete a feature given the feature id and the user id.
     *
     * @param featureId Unique identifier of the feature.
     * @param userId    Unique identifier of the user.
     */
    @Override
    public void deleteFeatureById(String featureId, String userId) {

        FeatureManagerDAO featureManagerDAO = new FeatureManagerDAOImpl();
        featureManagerDAO.deleteFeatureById(featureId, getTenantDomainFromCarbonContext(), userId);
    }

    /**
     * Checks the status of the feature. Whether the feature is locked or unlocked.
     *
     * @param featureId Unique identifier of the the feature.
     * @param userId    Unique identifier of the user.
     * @return The status of the feature.
     */
    @Override
    public boolean isFeatureLocked(String featureId, String userId) {

        Feature feature = fetchFeatureById(featureId, userId);
        long unlockTime = feature.getFeatureUnlockTime();
        if (System.currentTimeMillis() > unlockTime) {
            unlockFeature(feature);
        }
        return feature.isFeatureLocked();
    }

    /**
     * Get the reason/s for locking a feature given the feature id.
     *
     * @param featureId Unique identifier of the the feature.
     * @param userId    Unique identifier of the user.
     * @return The feature lock reason.
     */
    @Override
    public String[] getFeatureLockReason(String featureId, String userId) {

        Feature feature = fetchFeatureById(featureId, userId);
        return feature.getFeatureLockReason();
    }

    /**
     * Lock a feature given the feature id and the feature lock reason/s.
     *
     * @param featureId             Unique identifier of the feature.
     * @param userId                Unique identifier of the user.
     * @param featureLockReasonCode The reason/s for locking the feature.
     */
    @Override
    public void lockFeature(String featureId, String userId, String[] featureLockReasonCode) {

        long unlockTimePropertyValue = FeatureMgtConstants.UNLOCK_TIME_DEFAULT_VALUE;
        long unlockTime = System.currentTimeMillis() + unlockTimePropertyValue;
        Feature feature = fetchFeatureById(featureId, userId);
        feature.setFeatureLocked(Boolean.TRUE);
        feature.setFeatureLockReasonCode(featureLockReasonCode);
        feature.setFeatureUnlockTime(unlockTime);
        FeatureManager featureManager = new FeatureManagerImpl();
        featureManager.updateFeatureById(featureId, feature);
    }

    /**
     * Unlock a feature given the feature id and the user id.
     *
     * @param featureId Unique identifier of the feature.
     * @param userId    Unique identifier of the user.
     */
    @Override
    public void unlockFeatureById(String featureId, String userId) {

        Feature feature = fetchFeatureById(featureId, userId);
        unlockFeature(feature);
    }

    private Feature fetchFeatureById(String featureId, String userId) {

        FeatureManagerDAO featureManagerDAO = new FeatureManagerDAOImpl();
        return featureManagerDAO.getFeatureById(featureId, getTenantDomainFromCarbonContext(), userId);
    }

    private void unlockFeature(Feature feature) {

        feature.setFeatureLocked(Boolean.FALSE);
        feature.setFeatureUnlockTime(0);
        feature.setFeatureLockReason(null);
        feature.setFeatureLockReasonCode(null);
        updateFeatureById(feature.getFeatureId(), feature);
    }
}
