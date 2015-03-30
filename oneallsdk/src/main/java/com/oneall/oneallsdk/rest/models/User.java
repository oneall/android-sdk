package com.oneall.oneallsdk.rest.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * This class represents user object and mirrors JSON object returned by either connection.json or
 * user.json
 *
 * @author Uri Kogan
 *
 * @see <a href="https://docs.oneall.com/api/resources/connections/read-connection-details/">OneAll- read connection details</a>
 *
 * @see <a href="https://docs.oneall.com/api/resources/users/read-user-details/">OneAll- read user details</a>
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
            public String size;
        }

        public class Email implements Serializable {
            public String value;
            public Boolean isVerified;
        }

        public String identityToken;

        public String provider;

        public String id;

        @SerializedName("displayName")
        public String displayName;

        public Name name;

        @SerializedName("preferredUsername")
        public String preferredUsername;

        @SerializedName("thumbnailUrl")
        public String thumbnailUrl;

        @SerializedName("pictureUrl")
        public String pictureUrl;

        @SerializedName("profileUrl")
        public String profileUrl;

        public String gender;

        public String birthday;

        @SerializedName("utcOffset")
        public String utcOffset;

        public List<Email> emails;

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
