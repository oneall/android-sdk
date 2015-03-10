package com.oneall.oneallsdk.rest.service;

import com.oneall.oneallsdk.rest.models.ResponseProvidersList;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by urk on 9/3/15.
 */
public interface ProviderService {

    // region providers list API

    @GET("/providers.json")
    void listProviders(Callback<ResponseProvidersList> cb);

    // endregion
}
