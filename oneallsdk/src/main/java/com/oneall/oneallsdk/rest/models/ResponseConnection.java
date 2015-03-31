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

        public Connection connection;
        public User user;
    }

    public Status status;
    public Data data;
}
