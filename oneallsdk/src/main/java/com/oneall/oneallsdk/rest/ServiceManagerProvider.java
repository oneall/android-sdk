package com.oneall.oneallsdk.rest;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oneall.oneallsdk.Settings;
import com.oneall.oneallsdk.rest.service.ConnectionService;
import com.oneall.oneallsdk.rest.service.ProviderService;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Manager of services creates API access services
 */
public class ServiceManagerProvider {

    // region Properties

    private static ServiceManagerProvider mInstance = null;

    private final RestAdapter restAdapter;

    // endregion

    // region Lifecycle

    private ServiceManagerProvider() {
        String endpoint =
                String.format("https://%s.api.oneall.com", Settings.getInstance().getSubdomain());

        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new ItemTypeAdapterFactory())
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .create();

        restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(endpoint)
                .setConverter(new GsonConverter(gson))
                .build();
    }

    public static ServiceManagerProvider getInstance() {
        if (mInstance == null) {
            synchronized (ServiceManagerProvider.class) {
                if (mInstance == null && Settings.getInstance().getSubdomain() != null) {
                    mInstance = new ServiceManagerProvider();
                }
            }
        }
        return mInstance;
    }

    // endregion

    // region Interface methods

    public ProviderService getService() {
        return restAdapter.create(ProviderService.class);
    }

    public ConnectionService getConnectionService() {
        return restAdapter.create(ConnectionService.class);
    }

    public static String buildAuthHeader(String nonce) {
        return String.format("OneAllNonce %s", nonce);
    }

    // endregion
}
