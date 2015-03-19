package com.oneall.oneallsdk.rest.service;

import com.oneall.oneallsdk.rest.models.NativeLoginRequest;
import com.oneall.oneallsdk.rest.models.ResponseConnection;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.PUT;

/**
 * interface used to retrieve user information form OneAll API
 */
public interface UserService {
    @PUT("/users.json")
    void info(@Body NativeLoginRequest requestBody, Callback<ResponseConnection> callback);
}
