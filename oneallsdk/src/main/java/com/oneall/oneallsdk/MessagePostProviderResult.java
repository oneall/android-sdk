package com.oneall.oneallsdk;

/**
 * Created by urk on 9/3/15.
 */
public class MessagePostProviderResult {
    private Boolean success;
    private String flag;
    private Integer code;
    private String message;
    private String provider;

    public MessagePostProviderResult(
            Boolean success,
            String flag,
            Integer code,
            String message,
            String provider) {
        this.success = success;
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.provider = provider;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getFlag() {
        return flag;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getProvider() {
        return provider;
    }
}