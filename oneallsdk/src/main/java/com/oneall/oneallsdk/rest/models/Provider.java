package com.oneall.oneallsdk.rest.models;

import java.io.Serializable;

/**
 * Created by urk on 9/3/15.
 */
public class Provider implements Serializable {
    public class Configuration implements Serializable {
        private Boolean isRequired;
        private Boolean isCompleted;

        public Boolean getIsRequired() {
            return isRequired;
        }

        public void setIsRequired(Boolean isRequired) {
            this.isRequired = isRequired;
        }

        public Boolean getIsCompleted() {
            return isCompleted;
        }

        public void setIsCompleted(Boolean isCompleted) {
            this.isCompleted = isCompleted;
        }
    }

    public class Authentication implements Serializable {
        private Boolean isUserInputRequired;
        private String userInputType;

        public Boolean getIsUserInputRequired() {
            return isUserInputRequired;
        }

        public void setIsUserInputRequired(Boolean isUserInputRequired) {
            this.isUserInputRequired = isUserInputRequired;
        }

        public String getUserInputType() {
            return userInputType;
        }

        public void setUserInputType(String userInputType) {
            this.userInputType = userInputType;
        }
    }

    private String key;
    private String name;
    private Boolean isConfigurable;
    private Configuration configuration;
    private Authentication authentication;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsConfigurable() {
        return isConfigurable;
    }

    public void setIsConfigurable(Boolean isConfigurable) {
        this.isConfigurable = isConfigurable;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    @Override
    public String toString() {
        return String.format("%s- %s", super.toString(), key);
    }
}
