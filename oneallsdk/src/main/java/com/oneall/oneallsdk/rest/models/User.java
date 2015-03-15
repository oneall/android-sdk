package com.oneall.oneallsdk.rest.models;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by urk on 15/3/15.
 */
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

    public class PublishToken implements Serializable {
        public String key;
        public String date_creation;
        public String date_expiration;
    }

    public String uuid;
    public String userToken;
    public PublishToken publishToken;
    public Identity identity;
    public Collection<Identity> identies;
}
