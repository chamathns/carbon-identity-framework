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

public class Feature {

    private String featureId;
    private String featureType;
    private boolean isFeatureLocked;
    private int featureUnlockTime;
    private String[] featureLockReasonCode;
    private String[] featureLockReason;

    public Feature(String featureId, String featureType, boolean isFeatureLocked, int featureUnlockTime,
                   String[] featureLockReasonCode, String[] featureLockReason) {

        this.featureId = featureId;
        this.featureType = featureType;
        this.isFeatureLocked = isFeatureLocked;
        this.featureUnlockTime = featureUnlockTime;
        this.featureLockReasonCode = featureLockReasonCode;
        this.featureLockReason = featureLockReason;
    }

    public String getFeatureType() {

        return featureType;
    }

    public boolean isFeatureLocked() {

        return isFeatureLocked;
    }

    public int getFeatureUnlockTime() {

        return featureUnlockTime;
    }

    public String[] getFeatureLockReasonCode() {

        return featureLockReasonCode;
    }

    public String[] getFeatureLockReason() {

        return featureLockReason;
    }

    public void setFeatureType(String featureType) {

        this.featureType = featureType;
    }

    public void setFeatureLocked(boolean featureLocked) {

        isFeatureLocked = featureLocked;
    }

    public void setFeatureUnlockTime(int featureUnlockTime) {

        this.featureUnlockTime = featureUnlockTime;
    }

    public void setFeatureLockReasonCode(String[] featureLockReasonCode) {

        this.featureLockReasonCode = featureLockReasonCode;
    }

    public void setFeatureLockReason(String[] featureLockReason) {

        this.featureLockReason = featureLockReason;
    }
}
