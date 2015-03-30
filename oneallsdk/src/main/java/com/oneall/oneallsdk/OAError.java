package com.oneall.oneallsdk;

/**
 * Common error type returned by the manager and passing message between modules
 */
public class OAError {
    /** error codes */
    public enum ErrorCode {
        /** login has been cancelled by user */
        OA_ERROR_CANCELLED,

        /** authentication failed */
        OA_ERROR_AUTH_FAIL,

        /** failed to post message */
        OA_ERROR_MESSAGE_POST_FAIL,

        /** communication with server timeout */
        OA_ERROR_TIMEOUT,

        /** failure to retrieve user details after successful authentication */
        OA_ERROR_CONNECTION_ERROR
    }

    /** detailed human readable message */
    private String message;

    /** error code */
    private ErrorCode code;

    /**
     * default constructor
     *
     * @param code error code
     *
     * @param message human readable message
     */

    public OAError(ErrorCode code, String message) {
        this.message = message;
        this.code = code;
    }

    /**
     * getter of human readable error message
     *
     * @return a message
     */
    public String getMessage() {
        return message;
    }

    /**
     * getter of error code
     *
     * @return an error code
     */
    public ErrorCode getCode() {
        return code;
    }
}
