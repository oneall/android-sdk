package com.oneall.oneallsdk;

/**
 * Shared settings wrapper
 */
public class Settings {

    // region Properties

    private static Settings mInstance = null;

    // endregion

    // region Settings properties

    private String subdomain;

    // endregion

    // region Lifecycle

    public static Settings getInstance() {
        if (mInstance == null) {
            synchronized (Settings.class) {
                if (mInstance == null) {
                    mInstance = new Settings();
                }
            }
        }
        return mInstance;
    }


    // endregion

    // region Settings getters/setters

    public String getSubdomain() {
        return subdomain;
    }
    public void setSubdomain(String subdomain) {
        this.subdomain = subdomain;
    }

    // endregion
}
