package com.oneall.oneallsdk.rest;

import com.oneall.oneallsdk.rest.models.PostMessageResponse;

import retrofit.Callback;
import retrofit.RetrofitError;


/** callback processor of serrvice API */
public abstract class ServiceCallback<T> implements Callback<T> {

    public class ServiceError {
        private RetrofitError retrofitError;
        private PostMessageResponse response;

        public ServiceError(RetrofitError retrofitError, PostMessageResponse response) {
            this.retrofitError = retrofitError;
            this.response = response;
        }

        public RetrofitError getRetrofitError() {
            return retrofitError;
        }

        public PostMessageResponse getResponse() {
            return response;
        }
    }

    public abstract void failure(ServiceError restError);

    @Override
    @SuppressWarnings("unchecked")
    public void failure(RetrofitError error)
    {
        PostMessageResponse msgResp =
                (PostMessageResponse) error.getBodyAs(PostMessageResponse.class);

        ServiceError postError = new ServiceError(error, msgResp);
        failure(postError);
    }
}
