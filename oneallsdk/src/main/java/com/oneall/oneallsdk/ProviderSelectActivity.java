package com.oneall.oneallsdk;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.oneall.oneallsdk.rest.models.Provider;

import java.util.Collection;


public class ProviderSelectActivity
        extends ActionBarActivity
        implements ProviderFragment.OnFragmentInteractionListener {

    // region Constants

    private static final String TAG = ProviderSelectActivity.class.toString();

    public static final String INTENT_EXTRA_PROVIDER = "provider";

    // endregion

    // region Properties
    // endregion

    // region Lifecycle
    // endregion

    // region Activity Lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_select);

        setupTable();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            setResult(RESULT_CANCELED, new Intent());
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    // endregion

    // region Activity setup

    private void setupTable() {
        TableLayout table = (TableLayout) findViewById(R.id.activity_provider_select_table_view);

        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();

        Collection<Provider> providers = ProviderManager.getInstance().getProviders();

        if (providers != null && !providers.isEmpty()) {
            setupFullTable(table, fTrans, providers);
        } else {
            setupEmptyTable(table);
        }

        fTrans.commit();
    }

    private void setupEmptyTable(TableLayout table) {
        TableRow tableRow = new TableRow(this);
        TextView tv = new TextView(this);
        tv.setText(getResources().getString(R.string.providers_not_ready_try_again));
        tableRow.addView(tv);
        table.addView(tableRow);
    }

    private void setupFullTable(TableLayout table, FragmentTransaction fTrans, Collection<Provider> providers) {
        for (Provider p : providers) {
            if (p.getConfiguration() != null && p.getConfiguration().getIsCompleted()) {
                TableRow tableRow = new TableRow(this);
                tableRow.setTag(p.getKey());
                tableRow.setId(p.getKey().hashCode());
                table.addView(tableRow);


                /* add provider fragment to table row */
                ProviderFragment pf = ProviderFragment.newInstance(p.getName(), p.getKey());
                fTrans.add(R.id.activity_provider_select_table_view, pf);
            }
        }
    }

    // endregion

    // region UI Events handler
    // endregion

    // region ProviderFragment.OnFragmentInteractionListener

    @Override
    public void onFragmentInteraction(String providerKey) {
        OALog.info("Selected provider: " + providerKey);

        Intent response = new Intent();
        response.putExtra(INTENT_EXTRA_PROVIDER, providerKey);
        setResult(RESULT_OK, response);
        finish();

    }

    // endregion
}
