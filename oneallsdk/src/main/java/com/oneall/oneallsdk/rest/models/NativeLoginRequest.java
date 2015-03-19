package com.oneall.oneallsdk.rest.models;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/** model of object sent to OneAll API server to login using access token received from native
 * authentication */
public class NativeLoginRequest {
    public class Request {
        public class User {
            public class Identity {
                public class Source {
                    public class AccessToken {
                        public String key;

                        public AccessToken(String accessToken) {
                            this.key = accessToken;
                        }
                    }
                    public String key;
                    public AccessToken accessToken;

                    public Source(String provider, String accessToken) {
                        this.key = provider;
                        this.accessToken = new AccessToken(accessToken);
                    }
                }
                public Source source;

                public Identity(String provider, String accessToken) {
                    this.source = new Source(provider, accessToken);
                }
            }
            public String action = "import_from_access_token";
            public Identity identity;

            public User(String provider, String accessToken) {
                this.identity = new Identity(provider, accessToken);
            }
        }
        public User user;

        public Request(String provider, String accessToken) {
            this.user = new User(provider, accessToken);
        }
    }
    public Request request;

    public NativeLoginRequest(String provider, String accessToken) {
        this.request = new Request(provider, accessToken);
    }
}
