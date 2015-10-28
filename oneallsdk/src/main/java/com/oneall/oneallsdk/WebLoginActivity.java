package com.oneall.oneallsdk;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Web view activity used to take the user through authentication
 */
public class WebLoginActivity extends AppCompatActivity {

    // region Properties

    private ProgressDialog progressDialog;

    private WebView mWebView;
    // endregion

    // region Constants

    public final static String INTENT_EXTRA_URL = "url";

    private final static String CUSTOM_URL_SCHEME = "oneall";

    public final static int RESULT_FAILED = 0x80;

    // endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_login);

        final ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        mWebView = (WebView) findViewById(R.id.web_login_web_view);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return WebLoginActivity.this.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                OALog.info(String.format("Page loading started: %s", url));
                try {
                    if (progressDialog == null) {
                        progressDialog = ProgressDialog.show(
                                WebLoginActivity.this,
                                "",
                                getResources().getString(R.string.web_login_progress_title),
                                true,
                                true,
                                new OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        pageLoadFailed(null);
                                    }
                                });
                    }
                } catch (WindowManager.BadTokenException e) {
                    //ignore: the user backed out but we still got the onPageStarted event
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
        mWebView.loadUrl(getIntent().getExtras().getString(INTENT_EXTRA_URL));
    }

    @Override
    protected void onDestroy() {
        if (progressDialog != null) {
            // avoid leaking the progress window
            progressDialog.dismiss();
        }

        if (mWebView != null) {
            mWebView.stopLoading();
            mWebView.destroy();
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
