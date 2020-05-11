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

package org.wso2.carbon.identity.feature.mgt.model;

/**
 * A data model class to define the Feature element.
 */
public class Feature {

    private String featureId;
    private String userId;
    private String featureType;
    private boolean isFeatureLocked;
    private long featureUnlockTime;
    private String[] featureLockReasonCode;
    private String[] featureLockReason;

    public Feature(String featureId, String userId, String featureType, boolean isFeatureLocked, long featureUnlockTime,
                   String[] featureLockReasonCode, String[] featureLockReason) {

        this.featureId = featureId;
        this.userId = userId;
        this.featureType = featureType;
        this.isFeatureLocked = isFeatureLocked;
        this.featureUnlockTime = featureUnlockTime;
        this.featureLockReasonCode = featureLockReasonCode;
        this.featureLockReason = featureLockReason;
    }

    /**
     * Get feature ID.
     *
     * @return Feature ID.
     */
    public String getFeatureId() {

        return featureId;
    }

    /**
     * Get user ID.
     *
     * @return User ID.
     */
    public String getUserId() {

        return userId;
    }

    /**
     * Get feature type.
     *
     * @return Feature type.
     */
    public String getFeatureType() {

        return featureType;
    }

    /**
     * Get the status of the feature.
     *
     * @return The status of the feature.
     */
    public boolean isFeatureLocked() {

        return isFeatureLocked;
    }

    /**
     * Get the unlock time for the feature.
     *
     * @return The unlock time for the feature.
     */
    public long getFeatureUnlockTime() {

        return featureUnlockTime;
    }

    /**
     * Get the lock reason code/s for the feature.
     *
     * @return The lock reason code/s for the feature.
     */
    public String[] getFeatureLockReasonCode() {

        return featureLockReasonCode;
    }

    /**
     * Get the lock reason/s for the feature.
     *
     * @return The lock reason/s for the feature.
     */
    public String[] getFeatureLockReason() {

        return featureLockReason;
    }

    /**
     * Set the user ID of the feature.
     *
     * @param userId User ID.
     */
    public void setUserId(String userId) {

        this.userId = userId;
    }

    /**
     * Set the feature type of the feature.
     *
     * @param featureType Feature type.
     */
    public void setFeatureType(String featureType) {

        this.featureType = featureType;
    }

    /**
     * Set the feature status to locked/unlocked.
     *
     * @param featureLocked Status of the feature.
     */
    public void setFeatureLocked(boolean featureLocked) {

        isFeatureLocked = featureLocked;
    }

    /**
     * Set the unlock time for the feature.
     *
     * @param featureUnlockTime Unlock time for the feature.
     */
    public void setFeatureUnlockTime(long featureUnlockTime) {

        this.featureUnlockTime = featureUnlockTime;
    }

    /**
     * Set the lock reason code/s for the feature.
     *
     * @param featureLockReasonCode Lock reason code/s of the feature.
     */
    public void setFeatureLockReasonCode(String[] featureLockReasonCode) {

        this.featureLockReasonCode = featureLockReasonCode;
    }

    /**
     * Set the lock reason/s for the feature.
     *
     * @param featureLockReason Lock reason/s for the feature.
     */
    public void setFeatureLockReason(String[] featureLockReason) {

        this.featureLockReason = featureLockReason;
    }
}
