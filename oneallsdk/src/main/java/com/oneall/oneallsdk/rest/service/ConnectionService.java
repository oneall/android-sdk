package com.oneall.oneallsdk.rest.service;

import com.oneall.oneallsdk.rest.models.ResponseConnection;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;

/**
 * Interface used to pull connection information from OneAll:
 * http://docs.oneall.com/api/resources/connections/read-connection-details/
 */
public interface ConnectionService {
    @GET("/connection/{token}.json")
    void info(
            @Header("Authorization") String nonce,
            @Path("token") String token,
            Callback<ResponseConnection> cb);
}
