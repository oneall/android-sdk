package com.oneall.oneallsdk;

import com.oneall.oneallsdk.rest.ServiceManagerProvider;
import com.oneall.oneallsdk.rest.models.Provider;
import com.oneall.oneallsdk.rest.models.ResponseProvidersList;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Access to providers list. Retrieves list of providers asynchronously at the initialization and
 * stores in local cache. Retrieval is performed on every start and can be triggered by calling
 * {@link #refreshProviders(android.content.Context)}
 */
public class ProviderManager {

    // region constants

    private final String PROVIDERS_CACHE_FILE = "providers_cache";

    // endregion

    // region Properties

    private static ProviderManager mInstance;

    private Collection<Provider> providers;

    // endregion

    // region Lifecycle

    /**
     * get instance of {@code ProviderManager}
     *
     * @return a provider manager
     */
    public static ProviderManager getInstance() {
        if (mInstance == null) {
            synchronized (ProviderManager.class) {
                if (mInstance == null) {
                    mInstance = new ProviderManager();
                }
            }
        }
        return mInstance;
    }

    // endregion

    // region Interface methods

    /**
     * Updates the list of providers without any additional changes.
     *
     * @param providers The new list of providers
     */
    void updateProviders(List<Provider> providers) {
        this.providers = providers;
    }

    /**
     * force providers refresh. Should be executed as early as possible during application start
     *
     * @param context context to use for cache storage
     */
    public void refreshProviders(final Context context) {
        loadCachedProviders(context);

        ServiceManagerProvider.getInstance().getService().listProviders(new Callback<ResponseProvidersList>() {
            @Override
            public void success(ResponseProvidersList responseProvidersList, Response response) {
                List<Provider> pps = responseProvidersList
                        .getData()
                        .getProviders()
                        .getEntries();

                if(pps != null) {
                    cacheProviders(context, pps);
                    providers = pps;
                    OALog.info(String.format("Parsed %d providers from server", pps.size()));
                } else {
                    OALog.error("Failed to parse providers from server: got null");
                }
            }

            @Override
            public void failure(RetrofitError error) {
                OALog.warn(String.format("Failure to read providers list: %s", error.getMessage()));
            }
        });
    }

    /**
     * get cached list of providers
     *
     * @return list of providers if available; empty list if providers have not been cached yet
     */
    public Collection<Provider> getProviders() {
        if (providers != null) {
            return new ArrayList<>(providers);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * find provider object by its unique key
     *
     * @param key key to look up with (e.g. "{@code facebook}"
     *
     * @return provider with specified key or {@code null} on failure
     */
    public Provider findByKey(String key) {
        if (providers == null) {
            return null;
        }
        for (Provider p : providers) {
            if (p.getKey().equals(key)) {
                return p;
            }
        }
        return null;
    }

    // endregion

    // region Utilities

    /**
     * cache providers on local store to be saved between sessions
     *
     * @param context context to use for file storage
     *
     * @param providers providers collection to cache
     */
    private void cacheProviders(Context context, Collection<Provider> providers) {
        FileOutputStream fos = null;
        ObjectOutputStream os = null;
        try {
            fos = context.openFileOutput(PROVIDERS_CACHE_FILE, Context.MODE_PRIVATE);
            os = new ObjectOutputStream(fos);
            os.writeObject(providers);
        } catch (IOException e) {
            OALog.error("Cannot cache providers: " + e.getMessage());
        } finally {
            if (os != null) {
                try { os.close(); } catch (IOException ignored) { }
            }
            if (fos != null) {
                try { fos.close(); } catch (IOException ignored) { }
            }
        }
    }

    /**
     * load cached providers from local store. The result is stored in local property and can be
     * retrieved using {@link #getProviders()}
     *
     * @param context context to use for file load
     */
    private void loadCachedProviders(Context context) {
        FileInputStream fis = null;
        ObjectInputStream is = null;
        if (context == null) {
            return;
        }
        try {
            fis = context.openFileInput(PROVIDERS_CACHE_FILE);
            is = new ObjectInputStream(fis);
            Collection<Provider> tmp = (Collection<Provider>) is.readObject();

            if (tmp != null && !tmp.isEmpty()) {
                providers = tmp;
                OALog.info(String.format("Loaded %d cached providers", providers.size()));
            }
        } catch (FileNotFoundException ignored) {
            /* do nothing, no cached version of providers list */
        } catch (IOException | ClassNotFoundException e) {
            OALog.error(String.format("Could not load cached providers list %s", e.getMessage()));
        } finally {
            if (is != null) {
                try { is.close(); }
                catch (IOException ignored) { }
            }
            if (fis != null) {
                try { fis.close(); }
                catch (IOException ignored) { }
            }
        }
    }

    // endregion
}
