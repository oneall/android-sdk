package com.oneall.oneallsdk;

import android.content.Context;

import com.oneall.oneallsdk.rest.ServiceManagerProvider;
import com.oneall.oneallsdk.rest.models.ResponseProvidersList;
import com.oneall.oneallsdk.rest.models.Provider;

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
 * Created by urk on 9/3/15.
 *
 * Access to providers list
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

    public void refreshProviders(final Context context) {
        loadCachedProviders(context);

        ServiceManagerProvider.getInstance().getService().listProviders(new Callback<ResponseProvidersList>() {
            @Override
            public void success(ResponseProvidersList responseProvidersList, Response response) {
                List<Provider> pps = responseProvidersList
                        .getData()
                        .getProviders()
                        .getEntries();

                cacheProviders(context, pps);
                providers = pps;
                OALog.info(String.format("Parsed %d providers from server", pps.size()));
            }

            @Override
            public void failure(RetrofitError error) {
                OALog.warn(String.format("Failure to read providers list: %s", error.getMessage()));
            }
        });
    }

    public Collection<Provider> getProviders() {
        if (providers != null) {
            return new ArrayList<>(providers);
        } else {
            return new ArrayList<>();
        }
    }

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

    private void loadCachedProviders(Context context) {
        FileInputStream fis = null;
        ObjectInputStream is = null;
        if (context == null) {
            return;
        }
        try {
            fis = context.openFileInput(PROVIDERS_CACHE_FILE);
            is = new ObjectInputStream(fis);
            providers = (Collection<Provider>) is.readObject();

            if (providers != null) {
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
