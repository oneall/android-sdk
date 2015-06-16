package com.oneall.oneallsample;

import com.oneall.oneallsdk.OAError;
import com.oneall.oneallsdk.OAManager;
import com.oneall.oneallsdk.rest.models.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class OAMainActivity extends ActionBarActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private String mTwitterKey;
    private String mTwitterSecret;
    private String mSubdomain;

    // region Properties

    private User user;
    private Button buttonPost;
    private ImageView imageUserAvatar;
    private TextView textUserName;

    // endregion

    private OAManager.LoginHandler loginHandler = new OAManager.LoginHandler() {
        @Override
        public void loginSuccess(User user, Boolean newUser) {
            Log.v(OAMainActivity.class.toString(), "successfully logged into OA");
            textUserName.setText(user.identity.name.formatted);
            new ImageDownloader(imageUserAvatar).execute(user.identity.pictureUrl);
            OAMainActivity.this.user = user;

            buttonPost.setEnabled(true);
        }

        @Override
        public void loginFailure(OAError error) {
            Log.v(OAMainActivity.class.toString(), "Failed to login into OA");
            buttonPost.setEnabled(false);
            textUserName.setText(null);
            imageUserAvatar.setImageDrawable(null);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        OAManager.getInstance().onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        OAManager.getInstance().onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OAManager.getInstance().onDestroy();
        OAManager.destroyInstance();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        OAManager.getInstance().onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_oamain);

        mTwitterKey = getString(R.string.twitter_consumer_key);
        mTwitterSecret = getString(R.string.twitter_consumer_secret);
        mSubdomain = getString(R.string.oneall_subdomain);

        OAManager.getInstance().setup(this, mSubdomain, mTwitterKey, mTwitterSecret);
        OAManager.getInstance().onCreate(this, savedInstanceState);

        imageUserAvatar = (ImageView) findViewById(R.id.main_activity_user_avatar);
        textUserName = (TextView) findViewById(R.id.main_activity_user_name);

        findViewById(R.id.button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OAManager.getInstance().login(OAMainActivity.this, loginHandler);
            }
        });

        findViewById(R.id.button_main_login_facebook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OAManager.getInstance().login(OAMainActivity.this, "facebook", loginHandler);
            }
        });

        findViewById(R.id.button_main_login_foursquare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OAManager.getInstance().login(OAMainActivity.this, "foursquare", loginHandler);
            }
        });

        buttonPost = (Button) findViewById(R.id.button_post);
        buttonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlerButtonPost();
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        OAManager.getInstance().onPostResume(this);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        OAManager.getInstance().onActivityResult(requestCode, resultCode, data);
    }

    private void handlerButtonPost() {
        Intent intent = new Intent(this, PostActivity.class);
        intent.putExtra(PostActivity.INTENT_EXTRA_USER, user);
        startActivity(intent);
    }
}
