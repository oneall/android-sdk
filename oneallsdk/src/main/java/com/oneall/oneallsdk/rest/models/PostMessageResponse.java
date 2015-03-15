package com.oneall.oneallsdk.rest.models;

import com.google.gson.annotations.SerializedName;

import java.util.Collection;

/**
 * Created by urk on 15/3/15.
 */
public class PostMessageResponse {
    public static class Data {
        public static class Message {
            public static class Publication {
                public static class Status {
                    public String flag;
                    public Integer code;
                    public String message;
                }

                public Status status;
                public String provider;
                public String userToken;
                public String identityToken;
                public String datePublication;
            }

            @SerializedName("sharing_message_token")
            public String sharingMessageToken;

            @SerializedName("publications")
            public Collection<Publication> publications;

            @SerializedName("date_creation")
            public String dateCreation;

            @SerializedName("date_last_published")
            public String dateLastPublished;
        }

        @SerializedName("message")
        public Message message;
    }

    @SerializedName("data")
    public Data data;
}
