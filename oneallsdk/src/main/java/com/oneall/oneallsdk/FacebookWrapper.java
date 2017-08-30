package com.oneall.oneallsdk;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.Collections;

/** This class wraps interface to Facebook and hides implementation details */
class FacebookWrapper {
    // region Helper classes and interfaces

    interface SessionStateListener {

        void success(String accessToken);

        void failure(OAError error);
    }

    // endregion

    // region Properties

    /** callback manager used to take care of session state changes */
    @NonNull
    private CallbackManager callbackManager;

    // endregion

    // region Lifecycle

    private static FacebookWrapper mInstance = null;

    private FacebookWrapper() {
        this.callbackManager = CallbackManager.Factory.create();
    }

    public static FacebookWrapper getInstance() {
        if (mInstance == null) {
            synchronized (FacebookWrapper.class) {
                if (mInstance == null) {
                    mInstance = new FacebookWrapper();
                }
            }
        }
        return mInstance;
    }

    // endregion

    // region Interface methods

    void login(@NonNull Activity activity, @NonNull final SessionStateListener listener) {
        LoginManager.getInstance().registerCallback(
                callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        listener.success(loginResult.getAccessToken().getToken());
                    }

                    @Override
                    public void onCancel() {
                        listener.failure(
                                new OAError(OAError.ErrorCode.OA_ERROR_CANCELLED, "Cancelled"));
                    }

                    @Override
                    public void onError(FacebookException error) {
                        listener.failure(new OAError(
                                OAError.ErrorCode.OA_ERROR_AUTH_FAIL, error.getMessage()));
                    }
                });

        LoginManager
                .getInstance()
                .logInWithReadPermissions(activity, Collections.singletonList("public_profile"));
    }

    // endregion

    // region Utilities

    private void storeFacebookAppId(String appId, Activity activity) {
        try {
            ApplicationInfo info = activity
                    .getPackageManager()
                    .getApplicationInfo(activity.getPackageName(), PackageManager.GET_META_DATA);

            Bundle bundle = info.metaData;
            bundle.putString("com.facebook.sdk.ApplicationId", appId);
        } catch (PackageManager.NameNotFoundException e) {
            OALog.warn("Unable to set Facebook Application ID");
        }
    }

    // endregion

    // region Activity lifecycle responders

    void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    // endregion
}
