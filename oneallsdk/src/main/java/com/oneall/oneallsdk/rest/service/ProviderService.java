package com.oneall.oneallsdk.rest.service;

import com.oneall.oneallsdk.rest.models.ResponseProvidersList;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Service used to retrieve list of providers from OneAll API
 */
public interface ProviderService {

    // region providers list API

    @GET("/providers.json")
    void listProviders(Callback<ResponseProvidersList> cb);

    // endregion
}
