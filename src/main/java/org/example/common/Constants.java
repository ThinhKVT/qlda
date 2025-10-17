package org.example.common;

/**
 * Application-wide constants
 */
public final class Constants {

    private Constants() {
        // Prevent instantiation
    }

    /**
     * Project related constants
     */
    public static final class Project {
        public static final String CODE_PREFIX = "DA-";
        public static final String CODE_FORMAT = "%03d";
        public static final int CODE_SEQUENCE_LENGTH = 3;

        private Project() {}
    }

    /**
     * Error messages
     */
    public static final class ErrorMessages {
        public static final String VALIDATION_FAILED = "Validation failed";
        public static final String INVALID_PARAMETER_TYPE = "Invalid parameter type";
        public static final String ACCESS_DENIED = "Access denied";
        public static final String PERMISSION_DENIED = "You don't have permission to perform this action";
        public static final String INVALID_REQUEST = "Invalid request";
        public static final String INTERNAL_SERVER_ERROR = "Internal server error";
        public static final String PROJECT_NOT_FOUND = "Project not found";
        public static final String INVALID_DATE_RANGE = "End date must be after or equal to start date";

        private ErrorMessages() {}
    }

    /**
     * API related constants
     */
    public static final class Api {
        public static final String BASE_PATH = "/api";
        public static final String PROJECTS_PATH = BASE_PATH + "/projects";
        public static final String PACKAGES_PATH = BASE_PATH + "/packages";
        public static final String DOCUMENTS_PATH = BASE_PATH + "/documents";
        public static final String AUTH_PATH = BASE_PATH + "/auth";

        private Api() {}
    }

    /**
     * Pagination defaults
     */
    public static final class Pagination {
        public static final int DEFAULT_PAGE = 0;
        public static final int DEFAULT_SIZE = 10;
        public static final int MAX_SIZE = 100;

        private Pagination() {}
    }
}

