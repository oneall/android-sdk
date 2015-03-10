package com.oneall.oneallsdk;

import android.content.Context;
import android.util.Log;

import com.logentries.android.AndroidLogger;

/**
 * Created by urk on 9/3/15.
 */
public class OALog {

    // region Properties

    private static OALog mInstance = null;

    private AndroidLogger logger = null;

    // endregion

    // region Lifecycle

    private OALog() {
    }

    private OALog(Context context) {
        logger = AndroidLogger.getLogger(context, context.getString(R.string.logentries_token));
    }

    public static void init(Context context) {
        synchronized (OALog.class) {
            mInstance = new OALog(context);
        }
    }

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

    public static void info(String logMessage) {
        Log.i(OALog.class.toString(), logMessage);
        if (getInstance().logger != null) {
            getInstance().logger.info(logMessage);
        }
    }

    public static void warn(String logMessage) {
        Log.w(OALog.class.toString(), logMessage);
        if (getInstance().logger != null) {
            getInstance().logger.warn(logMessage);
        }
    }

    public static void error(String logMessage) {
        Log.e(OALog.class.toString(), logMessage);
        if (getInstance().logger != null) {
            getInstance().logger.error(logMessage);
        }
    }

    // endregion
}
