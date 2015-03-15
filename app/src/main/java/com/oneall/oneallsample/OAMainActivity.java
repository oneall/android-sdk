package com.oneall.oneallsample;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.oneall.oneallsdk.OAError;
import com.oneall.oneallsdk.OAManager;
import com.oneall.oneallsdk.rest.models.User;

import io.fabric.sdk.android.Fabric;

public class OAMainActivity extends ActionBarActivity {

    private User user;

    private OAManager.LoginHandler loginHandler = new OAManager.LoginHandler() {
        @Override
        public void loginSuccess(User user, Boolean newUser) {
            Log.v(OAMainActivity.class.toString(), "successfully logged into OA");
            ((TextView) findViewById(R.id.main_activity_user_name)).setText(user.identity.name.formatted);

            new ImageDownloader(((ImageView) findViewById(R.id.main_activity_user_avatar)))
                    .execute(user.identity.photos.get(0).value);

            OAMainActivity.this.user = user;
        }

        @Override
        public void loginFailure(OAError error) {
            Log.v(OAMainActivity.class.toString(), "Failed to login into OA");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_oamain);

        OAManager.getInstance(this).setup("urktest");

        findViewById(R.id.button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OAManager.getInstance().login(OAMainActivity.this, loginHandler);
            }
        });

        findViewById(R.id.button_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlerButtonPost();
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        OAManager.getInstance().onPostResume();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        OAManager.getInstance().onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_oamain, menu);
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

    private void handlerButtonPost() {
        Intent intent = new Intent(this, PostActivity.class);
        intent.putExtra(PostActivity.INTENT_EXTRA_USER, user);
        startActivity(intent);
    }
}
