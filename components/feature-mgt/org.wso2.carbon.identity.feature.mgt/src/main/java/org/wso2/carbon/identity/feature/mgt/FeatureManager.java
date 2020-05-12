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

import org.wso2.carbon.identity.feature.mgt.exception.FeatureManagementException;
import org.wso2.carbon.identity.feature.mgt.model.Feature;

/**
 * Feature manager service interface.
 *
 * @since 1.0.0
 */
public interface FeatureManager {

    /**
     * Return the feature info given the feature id and the user id.
     *
     * @param featureId Unique identifier of the feature.
     * @param userId    Unique identifier of the user.
     * @return {@link Feature}.
     * @throws FeatureManagementException
     */
    Feature getFeatureById(String featureId, String userId) throws FeatureManagementException;

    /**
     * Update a feature given the feature id by replacing the existing feature object.
     *
     * @param featureId Unique identifier of the the feature.
     * @param feature   Updated feature object.
     * @throws FeatureManagementException
     */
    void updateFeatureById(String featureId, Feature feature) throws FeatureManagementException;

    /**
     * Delete a feature given the feature id and the user id.
     *
     * @param featureId Unique identifier of the feature.
     * @param userId    Unique identifier of the user.
     * @throws FeatureManagementException
     */
    void deleteFeatureById(String featureId, String userId) throws FeatureManagementException;

    /**
     * Checks the status of the feature. Whether the feature is locked or unlocked.
     *
     * @param featureId Unique identifier of the the feature.
     * @param userId    Unique identifier of the user.
     * @return The status of the feature.
     * @throws FeatureManagementException
     */
    boolean isFeatureLocked(String featureId, String userId) throws FeatureManagementException;

    /**
     * Get the reason/s for locking a feature given the feature id.
     *
     * @param featureId Unique identifier of the the feature.
     * @param userId    Unique identifier of the user.
     * @return The feature lock reason.
     * @throws FeatureManagementException
     */
    String[] getFeatureLockReason(String featureId, String userId) throws FeatureManagementException;

    /**
     * Lock a feature given the feature id and the feature lock reason/s.
     *
     * @param featureId             Unique identifier of the feature.
     * @param userId                Unique identifier of the user.
     * @param featureLockReasonCode The reason/s for locking the feature.
     * @throws FeatureManagementException
     */
    void lockFeature(String featureId, String userId, String[] featureLockReasonCode) throws FeatureManagementException;

    /**
     * Unlock a feature given the feature id and the user id.
     *
     * @param featureId Unique identifier of the feature.
     * @param userId    Unique identifier of the user.
     * @throws FeatureManagementException
     */
    void unlockFeatureById(String featureId, String userId) throws FeatureManagementException;
}
