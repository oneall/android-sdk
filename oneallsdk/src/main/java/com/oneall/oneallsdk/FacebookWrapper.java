package com.oneall.oneallsdk;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.util.Arrays;

/** This class wraps interface to Facebook and hides implementation details */
public class FacebookWrapper {
    // region Helper classes and interfaces

    /**
     * handler responsible for Facebook status changes
     */
    private class SessionStatusCallback implements Session.StatusCallback {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    }

    public interface SessionStateListener {
        void success(String accessToken);
        void failure(OAError error);
    }

    // endregion

    // region Properties

    private Session.StatusCallback statusCallback = new SessionStatusCallback();

    private SessionStateListener mListener;

    private Session mSession;

    /** Facebook UI Helper */
    private UiLifecycleHelper uiHelper = null;

    // endregion

    // region Lifecycle

    private static FacebookWrapper mInstance = null;

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

    public boolean login(Activity activity, SessionStateListener listener) {
        mListener = listener;
        Session session = Session.getActiveSession();
        if (session != null) {
            if (!session.isOpened() && !session.isClosed()) {
                session.openForRead(new Session.OpenRequest(activity)
                        .setPermissions(Arrays.asList("public_profile"))
                        .setCallback(statusCallback));
            } else {
                Session.openActiveSession(activity, true, statusCallback);
            }
            return true;
        } else {
            return false;
        }
    }

    // endregion

    // region Utilities

    private void onSessionStateChange(Session session, SessionState sessionState, Exception ex) {
        OALog.info(String.format("Facebook login session change. New state: %s", sessionState));

        switch (sessionState) {
            case OPENED:
            case OPENED_TOKEN_UPDATED:
                if (isSessionChanged(session)) {
                    mSession = session;
                    onSessionSuccess(session);
                }
                break;
            case CLOSED_LOGIN_FAILED:
            case CLOSED:
                onSessionFailure(ex);
                break;
        }
    }

    private boolean isSessionChanged(Session session) {

        /* check if this is first activation */
        if (mSession == null) {
            return true;
        }

        // Check if session state changed
        if (mSession.getState() != session.getState())
            return true;

        // Check if accessToken changed
        if (mSession.getAccessToken() != null) {
            if (!mSession.getAccessToken().equals(session.getAccessToken()))
                return true;
        }
        else if (session.getAccessToken() != null) {
            return true;
        }

        // Nothing changed
        return false;
    }

    private void onSessionSuccess(Session session) {
        if (mListener != null) {
            mListener.success(session.getAccessToken());
        }
    }

    private void onSessionFailure(Exception ex) {
        if (mListener == null) {
            return;
        }
        // TODO: convert Facebook error message into local error message
        mListener.failure(new OAError(
                OAError.ErrorCode.OA_ERROR_AUTH_FAIL,
                (ex != null) ? ex.getMessage() : null));
    }

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

    public void onCreate(Activity activity, Bundle savedInstanceState) {
        uiHelper = new UiLifecycleHelper(activity, statusCallback);
        uiHelper.onCreate(savedInstanceState);
    }

    public void onDestroy() {
        if (uiHelper != null) {
            uiHelper.onDestroy();
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        if (uiHelper != null) {
            uiHelper.onSaveInstanceState(outState);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (uiHelper != null) {
            uiHelper.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onPause() {
        if (uiHelper != null) {
            uiHelper.onPause();
        }
    }

    public void onResume() {
        if (uiHelper != null) {
            Session session = Session.getActiveSession();
            if (session != null &&
                    (session.isOpened() || session.isClosed()) ) {
                onSessionStateChange(session, session.getState(), null);
            }
            uiHelper.onResume();
        }
    }

    // endregion
}
