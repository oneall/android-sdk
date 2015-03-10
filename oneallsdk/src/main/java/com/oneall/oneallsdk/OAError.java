package com.oneall.oneallsdk;

/**
 * Created by urk on 9/3/15.
 */
public class OAError {
    public enum ErrorCode {
        OA_ERROR_CANCELLED,
        OA_ERROR_INVALID_REQUEST,
        OA_ERROR_AUTH_FAIL,
        OA_ERROR_MESSAGE_POST_FAIL,
        OA_ERROR_TIMEOUT,
        OA_ERROR_CONNECTION_ERROR
    }

    private String message;
    private ErrorCode code;

    public OAError(ErrorCode code, String message) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public ErrorCode getCode() {
        return code;
    }
}
