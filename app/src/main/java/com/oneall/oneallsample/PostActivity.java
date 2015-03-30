package com.oneall.oneallsample;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.oneall.oneallsdk.OAError;
import com.oneall.oneallsdk.OAManager;
import com.oneall.oneallsdk.rest.models.PostMessageResponse;
import com.oneall.oneallsdk.rest.models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


public class PostActivity extends ActionBarActivity {

    // region Constants

    public final static String INTENT_EXTRA_USER = "user";

    private final static String TAG = PostActivity.class.toString();

    // endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().getExtras().get(INTENT_EXTRA_USER) == null) {
            finish();
        }

        findViewById(R.id.post_activity_button_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlerButtonPost();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void handlerButtonPost() {
        User user = (User) getIntent().getExtras().get(INTENT_EXTRA_USER);

        OAManager.getInstance().postMessage(
                ((EditText) findViewById(R.id.post_activity_text_text)).getText().toString(),
                ((EditText) findViewById(R.id.post_activity_text_picture)).getText().toString(),
                ((EditText) findViewById(R.id.post_activity_text_video)).getText().toString(),
                ((EditText) findViewById(R.id.post_activity_text_link)).getText().toString(),
                ((EditText) findViewById(R.id.post_activity_text_link_name)).getText().toString(),
                ((EditText) findViewById(R.id.post_activity_text_link_caption)).getText().toString(),
                ((EditText) findViewById(R.id.post_activity_text_link_description)).getText().toString(),
                true,
                user.userToken,
                user.publishToken.key,
                getProvidersForUser(user),
                new OAManager.OAManagerPostHandler() {
                    @Override
                    public void postComplete(Boolean success, PostMessageResponse response) {
                        postResponseComplete(success, response);
                    }
                }
        );
    }

    private Collection<String> getProvidersForUser(User user) {
        ArrayList<String> rv = new ArrayList<>();
        rv.add(user.identity.provider);
        return rv;
    }

    private void postResponseComplete(Boolean success, PostMessageResponse response) {
        ArrayList<String> failures = new ArrayList<>();
        for (PostMessageResponse.Data.Message.Publication pub : response.data.message.publications) {
            if (pub.status == null ||
                    pub.status.flag == null ||
                    !pub.status.flag.equals("success")) {
                failures.add(pub.provider);
            }
        }

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        };

        if (failures.size() > 0) {
            new AlertDialog.Builder(this)
                    .setTitle("Oops")
                    .setMessage("Failed providers list: " + failures)
                    .setPositiveButton("OK", listener)
                    .show();

        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Posted")
                    .setMessage("Message has been posted")
                    .setPositiveButton("OK", listener)
                    .show();
        }
    }
}
