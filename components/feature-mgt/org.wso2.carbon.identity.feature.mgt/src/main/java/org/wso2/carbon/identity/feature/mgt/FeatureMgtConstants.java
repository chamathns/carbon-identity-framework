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

/**
 * This class holds the constants used in the module, feature-mgt.
 */
public class FeatureMgtConstants {

    public static final long UNLOCK_TIME_DEFAULT_VALUE = 300000;

    public static final String MY_SQL = "MySQL";
    public static final String POSTGRE_SQL = "PostgreSQL";
    public static final String DB2 = "DB2";
    public static final String H2 = "H2";
    public static final String MICROSOFT = "Microsoft";
    public static final String S_MICROSOFT = "microsoft";

    /**
     * SQL Query definitions.
     */
    public static class SqlQueries {

        public static final String INSERT_FEATURE = "INSERT INTO IDN_FEATURE_MAPPING (TENANT_ID, USER_ID, " +
                "FEATURE_TYPE, IS_FEATURE_LOCKED, FEATURE_UNLOCK_TIME, FEATURE_LOCK_REASON, " +
                "FEATURE_LOCK_REASON_CODE) VALUES (?,?,?,?,?,?,?)";
        public static final String GET_FEATURE_BY_ID = "SELECT USER_ID, FEATURE_TYPE, IS_FEATURE_LOCKED, " +
                "FEATURE_UNLOCK_TIME, FEATURE_LOCK_REASON, FEATURE_LOCK_REASON_CODE" +
                "FROM IDN_FEATURE_MAPPING WHERE TENANT_ID=? AND USER_ID=? AND FEATURE_TYPE=?";
        public static final String UPDATE_FEATURE_BY_ID = "UPDATE IDN_FEATURE_MAPPING SET TENANT_ID=?, USER_ID=?, " +
                "FEATURE_TYPE=?, IS_FEATURE_LOCKED=?, FEATURE_UNLOCK_TIME=?, FEATURE_LOCK_REASON=?, " +
                "FEATURE_LOCK_REASON_CODE=?  WHERE TENANT_ID=? AND USER_ID=? AND FEATURE_TYPE=?";
        public static final String DELETE_FEATURE_BY_ID = "DELETE FROM IDN_FEATURE_MAPPING WHERE TENANT_ID=? " +
                "AND USER_ID=? AND FEATURE_TYPE=?";
    }

    /**
     * Enum definition for error messages.
     */
    public enum ErrorMessages {

        ERROR_CODE_INSERT_FEATURE("FM_001", "Error occurred while adding the feature: %s."),
        ERROR_CODE_SELECT_FEATURE_BY_ID("FM_002", "Error occurred while retrieving feature" +
                " from DB for tenant ID: %d, user ID: %s and feature type: %s."),
        ERROR_CODE_UPDATE_FEATURE("FM_003", "Error occurred while updating the feature: %s for " +
                "user ID: %s and tenant ID: %d."),
        ERROR_CODE_DELETE_FEATURE("FM_004", "Error occurred while deleting feature " +
                "from DB for tenant ID: %d, user ID: %s and feature type: %s.");

        private final String code;
        private final String message;

        /**
         * Error Messages.
         *
         * @param code    Code of the error message.
         * @param message Error message string.
         */
        ErrorMessages(String code, String message) {

            this.code = code;
            this.message = message;
        }

        public String getCode() {

            return code;
        }

        public String getMessage() {

            return message;
        }

        @Override
        public String toString() {

            return code + " : " + message;
        }
    }
}
