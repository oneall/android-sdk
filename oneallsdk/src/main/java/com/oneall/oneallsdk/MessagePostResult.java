package com.oneall.oneallsdk;

import java.util.Collection;

/**
 * Created by urk on 9/3/15.
 */
public class MessagePostResult {
    private Object wholeResponse;
    private String messageToken;
    private Collection<MessagePostProviderResult> providerResults;

    public MessagePostResult(
            Object wholeResponse,
            String messageToken,
            Collection<MessagePostProviderResult> providerResults) {
        this.wholeResponse = wholeResponse;
        this.messageToken = messageToken;
        this.providerResults = providerResults;
    }

    public Object getWholeResponse() {
        return wholeResponse;
    }

    public String getMessageToken() {
        return messageToken;
    }

    public Collection<MessagePostProviderResult> getProviderResults() {
        return providerResults;
    }
}
