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

import org.wso2.carbon.identity.feature.mgt.exception.FeatureManagementException;
import org.wso2.carbon.identity.feature.mgt.exception.FeatureManagementServerException;
import org.wso2.carbon.identity.feature.mgt.model.Feature;

/**
 * Perform CRUD operations for {@link Feature}.
 *
 * @since 1.0.0
 */
public interface FeatureManagerDAO {

    /**
     * Add a {@link Feature}.
     *
     * @param tenantId Unique identifier for the tenant domain.
     * @param userId   Unique identifier of the user.
     * @param feature  {@link Feature} to insert.
     * @throws FeatureManagementServerException If error occurs while adding the {@link Feature}.
     */
    void addFeature(int tenantId, String userId, Feature feature) throws FeatureManagementServerException;

    /**
     * Return the feature info given the feature id, tenant domain and the user id.
     *
     * @param featureId    Unique identifier of the feature.
     * @param tenantDomain Tenant Domain.
     * @param userId       Unique identifier of the user.
     * @return {@link Feature}.
     * @throws FeatureManagementServerException If error occurs while fetching the {@link Feature}.
     */
    Feature getFeatureById(String featureId, String tenantDomain, String userId)
            throws FeatureManagementServerException;

    /**
     * Update a feature given the feature id and tenant domain by replacing the existing feature object.
     *
     * @param featureId    Unique identifier of the the template.
     * @param tenantDomain Tenant Domain.
     * @param feature      Updated feature object.
     * @throws FeatureManagementServerException If error occurs while updating the {@link Feature}.
     */
    void updateFeatureById(String featureId, String tenantDomain, Feature feature)
            throws FeatureManagementServerException;

    /**
     * Delete a feature given the feature id, tenant domain and the user id.
     *
     * @param featureId    Unique identifier of the feature.
     * @param tenantDomain Tenant Domain.
     * @param userId       Unique identifier of the user.
     * @throws FeatureManagementServerException If error occurs while deleting the {@link Feature}.
     */
    void deleteFeatureById(String featureId, String tenantDomain, String userId)
            throws FeatureManagementServerException;
}
