package com.oneall.oneallsdk.rest.models;

import java.io.Serializable;
import java.util.List;

/**
 * data model for connection
 */
public class ResponseConnection implements Serializable {

    public class Status {
        public String flag;
        public Integer code;
        public String info;
    }

    public class Data {
        public class Connection implements Serializable {
            public String connectionToken;
            public String date;
            public String plugin;
        }

        public class User implements Serializable {
            public class Identity implements Serializable{

                public class Name implements Serializable {
                    public String formatted;
                }

                public class IdentityUrl implements Serializable {
                    public String value;
                    public String type;
                }

                public class Account implements Serializable {
                    public String domain;
                    public String userId;
                    public String username;
                }

                public class Photo implements Serializable {
                    public String value;
                    public String type;
                }

                public String identityToken;
                public String provider;
                public String id;
                public String displayName;
                public Name name;
                public String preferredUsername;
                public String thumbnailUrl;

                public List<IdentityUrl> urls;
                public List<Account> accounts;
                public List<Photo> photos;
            }

            public String userToken;
            public Identity identity;
        }

        public Connection connection;
        public User user;
    }

    public Status status;
    public Data data;
}
