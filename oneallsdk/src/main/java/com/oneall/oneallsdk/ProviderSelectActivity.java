package com.oneall.oneallsdk;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.oneall.oneallsdk.rest.models.Provider;


public class ProviderSelectActivity extends ActionBarActivity {

    // region Constants

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
        TableLayout table = (TableLayout) findViewById(R.id.table_layout);

        for (Provider p : ProviderManager.getInstance().getProviders()) {
            TableRow tableRow = new TableRow(this);
            tableRow.setTag(p.getKey());

            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handlerProviderSelected((String) v.getTag());
                }
            });

            TextView textView = new TextView(this);
            textView.setText(p.getName());
            tableRow.addView(textView);
            table.addView(tableRow);
        }
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

    private void handlerProviderSelected(String pkey) {
        Intent response = new Intent();
        response.putExtra(INTENT_EXTRA_PROVIDER, pkey);
        setResult(RESULT_OK, response);
        finish();
    }

    // endregion
}
