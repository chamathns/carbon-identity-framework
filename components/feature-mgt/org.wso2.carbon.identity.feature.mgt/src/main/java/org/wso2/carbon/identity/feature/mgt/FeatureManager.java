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
     */
    Feature getFeatureById(String featureId, String userId);

    /**
     * Update a feature given the feature id by replacing the existing feature object.
     *
     * @param featureId Unique identifier of the the feature.
     * @param feature   Updated feature object.
     */
    void updateFeatureById(String featureId, Feature feature);

    /**
     * Delete a feature given the feature id and the user id.
     *
     * @param featureId Unique identifier of the feature.
     * @param userId    Unique identifier of the user.
     */
    void deleteFeatureById(String featureId, String userId);

    /**
     * Checks the status of the feature. Whether the feature is locked or unlocked.
     *
     * @param featureId Unique identifier of the the feature.
     * @param userId    Unique identifier of the user.
     * @return The status of the feature.
     */
    boolean isFeatureLocked(String featureId, String userId);

    /**
     * Get the reason/s for locking a feature given the feature id.
     *
     * @param featureId Unique identifier of the the feature.
     * @param userId    Unique identifier of the user.
     * @return The feature lock reason.
     */
    String[] getFeatureLockReason(String featureId, String userId);

    /**
     * Lock a feature given the feature id and the feature lock reason/s.
     *
     * @param featureId             Unique identifier of the feature.
     * @param userId                Unique identifier of the user.
     * @param featureLockReasonCode The reason/s for locking the feature.
     */
    void lockFeature(String featureId, String userId, String[] featureLockReasonCode);
}
