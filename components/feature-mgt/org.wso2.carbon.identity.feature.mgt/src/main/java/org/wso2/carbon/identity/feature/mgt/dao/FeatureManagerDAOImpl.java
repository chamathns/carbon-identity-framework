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

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.StringJoiner;

import static org.wso2.carbon.identity.feature.mgt.FeatureMgtConstants.ErrorMessages.ERROR_CODE_DELETE_FEATURE;
import static org.wso2.carbon.identity.feature.mgt.FeatureMgtConstants.ErrorMessages.ERROR_CODE_INSERT_FEATURE;
import static org.wso2.carbon.identity.feature.mgt.FeatureMgtConstants.ErrorMessages.ERROR_CODE_SELECT_FEATURE_BY_ID;
import static org.wso2.carbon.identity.feature.mgt.FeatureMgtConstants.ErrorMessages.ERROR_CODE_UPDATE_FEATURE;

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
     * @throws FeatureManagementServerException If error occurs while adding the {@link Feature}.
     */
    @Override
    public void addFeature(int tenantId, String userId, Feature feature) throws FeatureManagementServerException {

        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();

        try {
            jdbcTemplate.executeUpdate(FeatureMgtConstants.SqlQueries.INSERT_FEATURE, preparedStatement -> {
                setPreparedStatementForFeature(tenantId, userId, feature, preparedStatement);
            });
        } catch (DataAccessException e) {
            throw FeatureMgtUtils.handleServerException(ERROR_CODE_INSERT_FEATURE, feature.getFeatureType(), e);
        }
    }

    /**
     * Return the feature info given the feature id, tenant domain and the user id.
     *
     * @param featureType Type of the feature.
     * @param tenantId    Unique identifier for the tenant domain.
     * @param userId      Unique identifier of the user.
     * @return {@link Feature}.
     * @throws FeatureManagementServerException If error occurs while fetching the {@link Feature}.
     */
    @Override
    public Feature getFeatureById(String featureType, int tenantId, String userId)
            throws FeatureManagementServerException {

        Feature feature;
        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
        try {
            feature = jdbcTemplate.fetchSingleRecord(FeatureMgtConstants.SqlQueries.GET_FEATURE_BY_ID,
                    ((resultSet, i) -> {
                        Feature featureResult = new Feature(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getBoolean(3),
                                resultSet.getInt(4),
                                stringToStringArray(resultSet.getString(5)),
                                stringToStringArray(resultSet.getString(6)));
                        return featureResult;
                    }),
                    preparedStatement -> {
                        preparedStatement.setInt(1, tenantId);
                        preparedStatement.setString(2, userId);
                        preparedStatement.setString(3, featureType);
                    });
        } catch (DataAccessException e) {
            throw new FeatureManagementServerException(String.format(ERROR_CODE_SELECT_FEATURE_BY_ID.getMessage(),
                    tenantId, userId, featureType), ERROR_CODE_SELECT_FEATURE_BY_ID.getCode(), e);
        }
        return feature;
    }

    /**
     * Update a feature given the feature id and tenant domain by replacing the existing feature object.
     *
     * @param featureType Type of the feature.
     * @param tenantId    Unique identifier for the tenant domain.
     * @param userId      Unique identifier of the user.
     * @param feature     Updated feature object.
     * @throws FeatureManagementServerException If error occurs while updating the {@link Feature}.
     */
    @Override
    public void updateFeatureById(String featureType, int tenantId, String userId, Feature feature)
            throws FeatureManagementServerException {

        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
        try {
            jdbcTemplate.executeUpdate(FeatureMgtConstants.SqlQueries.UPDATE_FEATURE_BY_ID, (preparedStatement -> {
                setPreparedStatementForFeature(tenantId, userId, feature, preparedStatement);
                preparedStatement.setInt(8, tenantId);
                preparedStatement.setString(9, userId);
                preparedStatement.setString(10, featureType);
            }));
        } catch (DataAccessException e) {
            throw new FeatureManagementServerException(String.format(ERROR_CODE_UPDATE_FEATURE.getMessage(),
                    featureType, userId, tenantId), ERROR_CODE_SELECT_FEATURE_BY_ID.getCode(), e);
        }
    }

    /**
     * Delete a feature given the feature id, tenant domain and the user id.
     *
     * @param featureType Type of the feature.
     * @param tenantId    Unique identifier for the tenant domain.
     * @param userId      Unique identifier of the user.
     * @throws FeatureManagementServerException If error occurs while deleting the {@link Feature}.
     */
    @Override
    public void deleteFeatureById(String featureType, int tenantId, String userId)
            throws FeatureManagementServerException {

        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
        try {
            jdbcTemplate.executeUpdate(FeatureMgtConstants.SqlQueries.DELETE_FEATURE_BY_ID, preparedStatement -> {
                preparedStatement.setInt(1, tenantId);
                preparedStatement.setString(2, userId);
                preparedStatement.setString(3, featureType);
            });
        } catch (DataAccessException e) {
            throw new FeatureManagementServerException(String.format(ERROR_CODE_DELETE_FEATURE.getMessage(), tenantId,
                    userId, featureType), ERROR_CODE_DELETE_FEATURE.getCode(), e);
        }
    }

    private String stringArrayToString(String[] strings) {

        StringJoiner joiner = new StringJoiner(",");
        for (String string : strings) {
            joiner.add(string);
        }
        return joiner.toString();
    }

    private String[] stringToStringArray(String string) {

        String[] strings = string.split(",");
        return strings;
    }

    private void setPreparedStatementForFeature(int tenantId, String userId, Feature feature,
                                                PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(1, tenantId);
        preparedStatement.setString(2, userId);
        preparedStatement.setString(3, feature.getFeatureType());
        preparedStatement.setBoolean(4, feature.isFeatureLocked());
        preparedStatement.setInt(5, Math.toIntExact(feature.getFeatureUnlockTime()));
        preparedStatement.setString(6, stringArrayToString(feature.getFeatureLockReason()));
        preparedStatement.setString(7, stringArrayToString(feature.getFeatureLockReasonCode()));
    }
}
