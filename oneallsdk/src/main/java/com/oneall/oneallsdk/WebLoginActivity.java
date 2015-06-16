package com.oneall.oneallsdk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Web view activity used to take the user through authentication
 */
public class WebLoginActivity extends ActionBarActivity {

    // region Properties

    private ProgressDialog progressDialog;

    // endregion

    // region Constants

    public final static String INTENT_EXTRA_URL = "url";

    private final static String CUSTOM_URL_SCHEME = "oneall";

    public final static Integer RESULT_FAILED = 0x80;

    // endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_login);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        WebView webView = (WebView) findViewById(R.id.web_login_web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return WebLoginActivity.this.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                OALog.info(String.format("Page loading state: %s", url));
                if (progressDialog == null) {
                    progressDialog = ProgressDialog.show(
                            WebLoginActivity.this,
                            "",
                            getResources().getString(R.string.web_login_progress_title));
                }

                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                pageLoadFinished(url);
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                pageLoadFailed(failingUrl);
            }
        });
        webView.loadUrl(getIntent().getExtras().getString(INTENT_EXTRA_URL));
    }

    @Override
    protected void onDestroy() {
        if (progressDialog != null) {
            // avoid leaking the progress window
            progressDialog.dismiss();
        }

        super.onDestroy();
    }

    private void pageLoadFailed(String url) {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }

        setResult(RESULT_FAILED);
        finish();
    }

    private void pageLoadFinished(String url) {
        OALog.info(String.format("Page loading complete: %s", url));
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    private boolean shouldOverrideUrlLoading(WebView view, String url) {
        OALog.info(String.format("Loading web request: %s", url));

        if (Uri.parse(url).getScheme().equalsIgnoreCase(CUSTOM_URL_SCHEME)) {
            OALog.info("OA Auth complete");

            view.stopLoading();

            Intent i = new Intent();
            i.putExtra(INTENT_EXTRA_URL, url);

            setResult(RESULT_OK, i);
            finish();
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_CANCELED);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
