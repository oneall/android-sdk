package com.oneall.oneallsdk;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import android.app.Activity;
import android.content.Intent;

/**
 * Class used to take care of Twitter authentication
 */
public class TwitterWrapper {

    // region Helper classes and interfaces

    /**
     * interface used for callbacks to the calling application
     */
    public interface LoginComplete {
        void success(String accessToken, String secret);
        void failure(OAError error);
    }

    // endregion

    // region Properties

    /** twitter authentication client */
    private TwitterAuthClient client;

    // endregion

    // region Lifecycle

    private static TwitterWrapper mInstance = null;

    private TwitterWrapper() {
        client = new TwitterAuthClient();
    }

    /**
     * get instance of this wrapper
     *
     * @return a wrapper of Twitter client
     */
    public static TwitterWrapper getInstance() {
        if (mInstance == null) {
            synchronized (TwitterWrapper.class) {
                if (mInstance == null) {
                    mInstance = new TwitterWrapper();
                }
            }
        }
        return mInstance;
    }

    public static void destroyInstance() {
        synchronized (TwitterWrapper.class) {
            if (mInstance != null) {
                mInstance = null;
            }
        }
    }

    // endregion

    // region Interface methods

    /**
     * Login into Twitter using native Android method and Twitter SDK
     *
     * @param activity activity to use during login
     *
     * @param callback callback to use to inform the caller about operation completion
     */
    public void login(Activity activity, final LoginComplete callback) {
        client.authorize(activity, new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> twitterSessionResult) {
                if (callback != null) {
                    callback.success(
                            twitterSessionResult.data.getAuthToken().token,
                            twitterSessionResult.data.getAuthToken().secret);
                }
            }

            @Override
            public void failure(TwitterException e) {
                if (callback != null) {
                    callback.failure(
                            new OAError(OAError.ErrorCode.OA_ERROR_AUTH_FAIL, e.getMessage()));
                }
            }
        });
    }

    /**
     * should be called by the user to process response callbacks from Twitter window
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        client.onActivityResult(requestCode, resultCode, data);
    }

    // endregion
}
