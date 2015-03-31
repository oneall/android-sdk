package com.oneall.oneallsdk.rest.models;

import java.util.Date;

/**
 * Created by urk on 9/3/15.
 */
public class ServiceResponseBase {

    public class Request {
        public class Status {
            public String flag;
            public Integer code;
            public String info;
        }

        public Date date;
        public String resource;
        public Status status;
    }

}
