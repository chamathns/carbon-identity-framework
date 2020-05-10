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

package org.wso2.carbon.identity.feature.mgt.dao;

import org.wso2.carbon.identity.feature.mgt.model.Feature;

/**
 * Feature manager DAO implementation.
 */
public class FeatureManagerDAOImpl implements FeatureManagerDAO {

    /**
     * Return the feature info given the feature id.
     *
     * @param featureId    unique identifier of the feature.
     * @param tenantDomain tenant Domain.
     * @return {@link Feature}.
     */
    @Override
    public Feature getFeatureById(String featureId, String tenantDomain) {

        return null;
    }

    /**
     * Update a feature given the feature id by replacing the existing feature object.
     *
     * @param featureId    unique identifier of the the template.
     * @param tenantDomain tenant Domain.
     * @param feature      updated feature object.
     */
    @Override
    public void updateFeatureById(String featureId, String tenantDomain, Feature feature) {

    }

    /**
     * Delete a feature given the feature id.
     *
     * @param featureId    unique identifier of the feature.
     * @param tenantDomain tenant Domain.
     */
    @Override
    public void deleteFeatureById(String featureId, String tenantDomain) {

    }
}
