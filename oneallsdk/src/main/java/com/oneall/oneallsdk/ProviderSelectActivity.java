package com.oneall.oneallsdk;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.oneall.oneallsdk.rest.models.Provider;


public class ProviderSelectActivity
        extends ActionBarActivity
        implements ProviderFragment.OnFragmentInteractionListener {

    // region Constants

    private static final String TAG = ProviderSelectActivity.class.toString();

    public static final String INTENT_EXTRA_PROVIDER = "provider";

    // endregion

    // region Properties

    private String loginHandlerId;

    // endregion

    // region Lifecycle
    // endregion

    // region Activity Lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_select);

        setupTable();
        setupButtons();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_provider_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // endregion

    // region Activity setup

    private void setupTable() {
        TableLayout table = (TableLayout) findViewById(R.id.activity_provider_select_table_view);

        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();

        for (Provider p : ProviderManager.getInstance().getProviders()) {
            TableRow tableRow = new TableRow(this);
            tableRow.setTag(p.getKey());
            tableRow.setId(p.getKey().hashCode());
            table.addView(tableRow);


            /* add provider fragment to table row */
            ProviderFragment pf = ProviderFragment.newInstance(p.getName(), p.getKey());
            fTrans.add(R.id.activity_provider_select_table_view /*tableRow.getId()*/, pf);
        }

        fTrans.commit();
    }

    private void setupButtons() {
        findViewById(R.id.button_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonHandlerCancel();
            }
        });
    }

    // endregion

    // region UI Events handler

    private void buttonHandlerCancel() {
        setResult(RESULT_CANCELED, new Intent());
        finish();
    }

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
