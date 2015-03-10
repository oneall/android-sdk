package com.oneall.oneallsdk.rest.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by urk on 9/3/15.
 */
public class ResponseProvidersList implements Serializable {

    public class Data implements Serializable {
        public class Providers implements Serializable {
            private Integer count;
            private List<Provider> entries;

            public Integer getCount() {
                return count;
            }

            public void setCount(Integer count) {
                this.count = count;
            }

            public List<Provider> getEntries() {
                return entries;
            }

            public void setEntries(List<Provider> entries) {
                this.entries = entries;
            }
        }

        private Providers providers;

        public Providers getProviders() {
            return providers;
        }

        public void setProviders(Providers providers) {
            this.providers = providers;
        }
    }

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
