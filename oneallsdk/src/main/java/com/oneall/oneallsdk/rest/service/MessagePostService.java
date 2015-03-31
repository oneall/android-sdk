package com.oneall.oneallsdk.rest.service;

import com.oneall.oneallsdk.rest.ServiceCallback;
import com.oneall.oneallsdk.rest.models.PostMessageRequest;
import com.oneall.oneallsdk.rest.models.PostMessageResponse;

import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by urk on 15/3/15.
 */
public interface MessagePostService {
    @POST("/users/{token}/publish.json")
    void post(
            @Path("token") String userToken,
            @Header("Authorization") String publishToken,
            @Body PostMessageRequest message,
            ServiceCallback<PostMessageResponse> callback);
}
