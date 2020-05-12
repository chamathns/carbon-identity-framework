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
import org.wso2.carbon.identity.feature.mgt.exception.FeatureManagementException;
import org.wso2.carbon.identity.feature.mgt.model.Feature;

import static org.wso2.carbon.identity.feature.mgt.utils.FeatureMgtUtils.getTenantIDFromCarbonContext;

/**
 * Feature manager service implementation.
 */
public class FeatureManagerImpl implements FeatureManager {

    /**
     * Add a feature against a user given the feature type, user id and the feature info.
     *
     * @param featureType Type of the feature.
     * @param userId      Unique identifier of the user.
     * @throws FeatureManagementException
     */
    @Override
    public void addFeatureForUser(String featureType, String userId)
            throws FeatureManagementException {

        Feature feature = new Feature(featureType, userId, Boolean.FALSE, 0, null, null);
        FeatureManagerDAO featureManagerDAO = new FeatureManagerDAOImpl();
        featureManagerDAO.addFeature(getTenantIDFromCarbonContext(), userId, feature);
    }

    /**
     * Return the feature info given the feature type and the user id.
     *
     * @param featureType Type of the feature.
     * @param userId      Unique identifier of the user.
     * @return {@link Feature}.
     * @throws FeatureManagementException
     */
    @Override
    public Feature getFeatureForUser(String featureType, String userId) throws FeatureManagementException {

        return fetchFeatureById(featureType, userId);
    }

    /**
     * Delete a feature given the feature type and the user id.
     *
     * @param featureType Type of the feature.
     * @param userId      Unique identifier of the user.
     * @throws FeatureManagementException
     */
    @Override
    public void deleteFeatureForUser(String featureType, String userId) throws FeatureManagementException {

        FeatureManagerDAO featureManagerDAO = new FeatureManagerDAOImpl();
        featureManagerDAO.deleteFeatureById(featureType, getTenantIDFromCarbonContext(), userId);
    }

    /**
     * Checks the status of the feature. Whether the feature is locked or unlocked given the feature type and the
     * user id.
     *
     * @param featureType Type of the the feature.
     * @param userId      Unique identifier of the user.
     * @return The status of the feature.
     * @throws FeatureManagementException
     */
    @Override
    public boolean isFeatureLockedForUser(String featureType, String userId) throws FeatureManagementException {

        Feature feature = fetchFeatureById(featureType, userId);
        long unlockTime = feature.getFeatureUnlockTime();
        if (System.currentTimeMillis() > unlockTime) {
            unlockFeature(feature, userId);
        }
        return feature.isFeatureLocked();
    }

    /**
     * Get the reason/s for locking a feature given the feature type and the user id.
     *
     * @param featureType Type of the feature.
     * @param userId      Unique identifier of the user.
     * @return The feature lock reason.
     * @throws FeatureManagementException
     */
    @Override
    public String[] getFeatureLockReasonForUser(String featureType, String userId) throws FeatureManagementException {

        Feature feature = fetchFeatureById(featureType, userId);
        return feature.getFeatureLockReason();
    }

    /**
     * Lock a feature given the feature type, user id, feature lock time and the feature lock reason/s.
     *
     * @param featureType           Type of the feature.
     * @param userId                Unique identifier of the user.
     * @param featureUnlockTime     The unlock time for the feature.
     * @param featureLockReasonCode The reason/s for locking the feature.
     * @throws FeatureManagementException
     */
    @Override
    public void lockFeatureForUser(String featureType, String userId, long featureUnlockTime, String[] featureLockReasonCode)
            throws FeatureManagementException {

        long unlockTime = System.currentTimeMillis() + featureUnlockTime;
        Feature feature = fetchFeatureById(featureType, userId);
        feature.setFeatureLocked(Boolean.TRUE);
        feature.setFeatureLockReasonCode(featureLockReasonCode);
        feature.setFeatureUnlockTime(unlockTime);
        FeatureManagerDAO featureManagerDAO = new FeatureManagerDAOImpl();
        featureManagerDAO.updateFeatureById(featureType, getTenantIDFromCarbonContext(), userId, feature);
    }

    /**
     * Unlock a feature given the feature type and the user id.
     *
     * @param featureType Type of the feature.
     * @param userId      Unique identifier of the user.
     * @throws FeatureManagementException
     */
    @Override
    public void unlockFeatureForUser(String featureType, String userId) throws FeatureManagementException {

        Feature feature = fetchFeatureById(featureType, userId);
        unlockFeature(feature, userId);
    }

    private Feature fetchFeatureById(String featureId, String userId) throws FeatureManagementException {

        FeatureManagerDAO featureManagerDAO = new FeatureManagerDAOImpl();
        return featureManagerDAO.getFeatureById(featureId, getTenantIDFromCarbonContext(), userId);
    }

    private void unlockFeature(Feature feature, String userId) throws FeatureManagementException {

        feature.setFeatureLocked(Boolean.FALSE);
        feature.setFeatureUnlockTime(0);
        feature.setFeatureLockReason(null);
        feature.setFeatureLockReasonCode(null);
        FeatureManagerDAO featureManagerDAO = new FeatureManagerDAOImpl();
        featureManagerDAO.updateFeatureById(feature.getFeatureType(), getTenantIDFromCarbonContext(), userId, feature);
    }
}
