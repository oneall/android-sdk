package com.oneall.oneallsdk;

import java.util.Collection;

/**
 * Class wrapping response for message posting API request. Mirrors JSON response described here:
 * {@link <a href="https://docs.oneall.com/api/resources/social-sharing/publish-new-message/">https://docs.oneall.com/api/resources/social-sharing/publish-new-message/</a>}
 */
public class MessagePostResult {
    /** Class wrapping result of posting message to wall for single provider. */
    public class MessagePostProviderResult {
        public Boolean success;
        public String flag;
        public Integer code;
        public String message;
        public String provider;

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
    }

    public Object wholeResponse;
    public String messageToken;
    public Collection<MessagePostProviderResult> providerResults;

    public MessagePostResult(
            Object wholeResponse,
            String messageToken,
            Collection<MessagePostProviderResult> providerResults) {
        this.wholeResponse = wholeResponse;
        this.messageToken = messageToken;
        this.providerResults = providerResults;
    }
}
