package com.oneall.oneallsdk;

import com.logentries.android.AndroidLogger;

import android.content.Context;
import android.util.Log;

/**
 * Wrapper class for error logger, able to handle multiple types of logs: LogEntries, regular
 * Android log. The class is used internally by OneAll SDK and should not be used by the external
 * application.
 */
class OALog {

    // region Constants

    private final static String TAG = "oneall";

    // endregion

    // region Properties

    private static OALog mInstance = null;

    private AndroidLogger logger = null;

    // endregion

    // region Lifecycle

    /** block creation of log using {@code new OALog()} by making constructor private */
    private OALog() {
    }

    /**
     * constructor of the instance
     *
     * @param context context to use for logging operations
     */
    private OALog(Context context) {
        if (BuildConfig.DEBUG) {
            logger = AndroidLogger.getLogger(context, context.getString(R.string.logentries_token));
        }
    }

    /**
     * initialization method, should be called before using {@code OALog}
     *
     * @param context context under which the log should run
     */
    public static void init(Context context) {
        synchronized (OALog.class) {
            mInstance = new OALog(context);
        }
    }

    /**
     * get instance of the log
     *
     * @return a log
     */
    public static OALog getInstance() {
        if (mInstance == null) {
            synchronized (OALog.class) {
                if (mInstance == null) {
                    mInstance = new OALog();
                }
            }
        }
        return mInstance;
    }

    // endregion

    // region Interface methods

    /**
     * post message with information level to log
     *
     * @param logMessage message to post
     */
    public static void info(String logMessage) {
        Log.i(TAG, logMessage);
        if (getInstance().logger != null) {
            getInstance().logger.info(logMessage);
        }
    }

    /**
     * post message with warning level to log
     *
     * @param logMessage message to post
     */
    public static void warn(String logMessage) {
        Log.w(TAG, logMessage);
        if (getInstance().logger != null) {
            getInstance().logger.warn(logMessage);
        }
    }

    /**
     * post message with error level to log
     *
     * @param logMessage message to post
     */
    public static void error(String logMessage) {
        Log.e(TAG, logMessage);
        if (getInstance().logger != null) {
            getInstance().logger.error(logMessage);
        }
    }

    // endregion
}
