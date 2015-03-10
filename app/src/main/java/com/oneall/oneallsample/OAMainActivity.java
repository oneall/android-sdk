package com.oneall.oneallsample;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.oneall.oneallsdk.OAError;
import com.oneall.oneallsdk.OAManager;
import com.oneall.oneallsdk.rest.models.ResponseConnection;

public class OAMainActivity extends ActionBarActivity {

    private OAManager.LoginHandler loginHandler = new OAManager.LoginHandler() {
        @Override
        public void loginSuccess(ResponseConnection.Data.User user, Boolean newUser) {
            Log.v(OAMainActivity.class.toString(), "successfully logged into OA");
            ((TextView) findViewById(R.id.main_activity_user_name)).setText(user.identity.name.formatted);

            new ImageDownloader(((ImageView) findViewById(R.id.main_activity_user_avatar)))
                    .execute(user.identity.photos.get(0).value);
        }

        @Override
        public void loginFailure(OAError error) {
            Log.v(OAMainActivity.class.toString(), "Failed to login into OA");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oamain);

        OAManager.getInstance(this).setup("urktest");

        Button loginButton = (Button) findViewById(R.id.button_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OAManager.getInstance().login(OAMainActivity.this, loginHandler);
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
}
