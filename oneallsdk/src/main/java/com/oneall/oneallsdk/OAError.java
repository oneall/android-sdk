package com.oneall.oneallsdk;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
    @Nullable private String message;

    /** error code */
    @NonNull private ErrorCode code;

    /**
     * default constructor
     *
     * @param code error code
     *
     * @param message human readable message
     */

    public OAError(@NonNull ErrorCode code, @Nullable String message) {
        this.message = message;
        this.code = code;
    }

    /**
     * getter of human readable error message
     *
     * @return a message
     */
    @Nullable public String getMessage() {
        return message;
    }

    /**
     * getter of error code
     *
     * @return an error code
     */
    @NonNull public ErrorCode getCode() {
        return code;
    }
}
