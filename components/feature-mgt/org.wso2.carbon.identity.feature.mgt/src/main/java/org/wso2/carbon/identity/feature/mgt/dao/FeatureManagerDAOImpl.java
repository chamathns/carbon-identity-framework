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

import org.wso2.carbon.database.utils.jdbc.JdbcTemplate;
import org.wso2.carbon.database.utils.jdbc.exceptions.DataAccessException;
import org.wso2.carbon.identity.feature.mgt.FeatureMgtConstants;
import org.wso2.carbon.identity.feature.mgt.exception.FeatureManagementServerException;
import org.wso2.carbon.identity.feature.mgt.model.Feature;
import org.wso2.carbon.identity.feature.mgt.utils.FeatureMgtUtils;
import org.wso2.carbon.identity.feature.mgt.utils.JdbcUtils;

import java.util.StringJoiner;

/**
 * Feature manager DAO implementation.
 */
public class FeatureManagerDAOImpl implements FeatureManagerDAO {


    /**
     * Add a {@link Feature}.
     *
     * @param tenantId Unique identifier for the tenant domain.
     * @param userId   Unique identifier of the user.
     * @param feature  {@link Feature} to insert.
     */
    @Override
    public void addFeature(int tenantId, String userId, Feature feature) throws FeatureManagementServerException {

        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();

        try {
            jdbcTemplate.executeUpdate(FeatureMgtConstants.SqlQueries.INSERT_FEATURE, preparedStatement -> {
                preparedStatement.setInt(1, tenantId);
                preparedStatement.setString(2, userId);
                preparedStatement.setString(3, feature.getFeatureType());
                preparedStatement.setBoolean(4, feature.isFeatureLocked());
                preparedStatement.setInt(5, Math.toIntExact(feature.getFeatureUnlockTime()));
                preparedStatement.setString(6, prepareStringArray(feature.getFeatureLockReason()));
                preparedStatement.setString(7, prepareStringArray(feature.getFeatureLockReasonCode()));
            });
        } catch (DataAccessException e) {
            throw FeatureMgtUtils.handleServerException(FeatureMgtConstants.ErrorMessages.ERROR_CODE_INSERT_FEATURE,
                    feature.getFeatureType(), e);
        }
    }

    /**
     * Return the feature info given the feature id, tenant domain and the user id.
     *
     * @param featureId    Unique identifier of the feature.
     * @param tenantDomain Tenant Domain.
     * @param userId       Unique identifier of the user.
     * @return {@link Feature}.
     */
    @Override
    public Feature getFeatureById(String featureId, String tenantDomain, String userId) {
        return null;
    }

    /**
     * Update a feature given the feature id and tenant domain by replacing the existing feature object.
     *
     * @param featureId    Unique identifier of the the template.
     * @param tenantDomain Tenant Domain.
     * @param feature      Updated feature object.
     */
    @Override
    public void updateFeatureById(String featureId, String tenantDomain, Feature feature) {

    }

    /**
     * Delete a feature given the feature id, tenant domain and the user id.
     *
     * @param featureId    Unique identifier of the feature.
     * @param tenantDomain Tenant Domain.
     * @param userId       Unique identifier of the user.
     */
    @Override
    public void deleteFeatureById(String featureId, String tenantDomain, String userId) {

    }

    private String prepareStringArray(String[] strings) {

        StringJoiner joiner = new StringJoiner(",");
        for (String string: strings) {
            joiner.add(string);
        }
        return joiner.toString();
    }
}
