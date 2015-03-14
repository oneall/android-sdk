package com.oneall.oneallsdk;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProviderFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProviderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProviderFragment extends Fragment {

    // region constants

    private static final String ARG_PARAM_PROVIDER_NAME = "mProviderName";
    private static final String ARG_PARAM_PROVIDER_KEY = "mProviderKey";

    // endregion

    // region Helper interfaces and classes
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String providerKey);
    }

    // endregion


    // region Properties

    private String mProviderName;

    private String mProviderKey;

    private OnFragmentInteractionListener mListener;

    // endregion

    // region Lifecycle

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param providerName Name fo the provider which will be put on screen
     *
     * @param providerKey Unique identifier of provider which will be used in call-back
     *
     * @return A new instance of fragment ProviderFragment.
     */
    public static ProviderFragment newInstance(
            String providerName,
            String providerKey) {
        ProviderFragment fragment = new ProviderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_PROVIDER_NAME, providerName);
        args.putString(ARG_PARAM_PROVIDER_KEY, providerKey);
        fragment.setArguments(args);
        return fragment;
    }

    public ProviderFragment() {
        // Required empty public constructor
    }

    // endregion

    // region Fragment overrides

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProviderName = getArguments().getString(ARG_PARAM_PROVIDER_NAME);
            mProviderKey = getArguments().getString(ARG_PARAM_PROVIDER_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rv = inflater.inflate(R.layout.fragment_provider, container, false);

        TextView textName = (TextView) rv.findViewById(R.id.provider_fragment_text_name);
        textName.setText(mProviderName);

        ImageView imageIcon = (ImageView) rv.findViewById(R.id.provider_fragment_image_view);
        imageIcon.setImageDrawable(getDrawable(mProviderKey));

        rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFragmentInteraction(mProviderKey);
            }
        });

        rv.setOnTouchListener(new View.OnTouchListener() {
            private Drawable mDefaultBackground;
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                    mDefaultBackground = v.getBackground();
                    v.setBackgroundColor(Color.LTGRAY);
                } else if (Build.VERSION.SDK_INT < 15) {
                    //noinspection deprecation
                    v.setBackgroundDrawable(mDefaultBackground);
                } else {
                    v.setBackground(mDefaultBackground);
                }
                return false;
            }
        });

        return rv;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    // endregion

    // region Utilities

    private Drawable getDrawable(String providerKey) {
        int rid = 0;

        if (providerKey.equals("amazon")) rid = R.drawable.button_login_amazon;
        if (providerKey.equals("blogger")) rid = R.drawable.button_login_blogger;
        if (providerKey.equals("disqus")) rid = R.drawable.button_login_disqus;
        if (providerKey.equals("facebook")) rid = R.drawable.button_login_facebook;
        if (providerKey.equals("foursquare")) rid = R.drawable.button_login_foursquare;
        if (providerKey.equals("github")) rid = R.drawable.button_login_github;
        if (providerKey.equals("google")) rid = R.drawable.button_login_google;
        if (providerKey.equals("instagram")) rid = R.drawable.button_login_instagram;
        if (providerKey.equals("linkedin")) rid = R.drawable.button_login_linkedin;
        if (providerKey.equals("livejournal")) rid = R.drawable.button_login_livejournal;
        if (providerKey.equals("mailru")) rid = R.drawable.button_login_mailru;
        if (providerKey.equals("odnoklassniki")) rid = R.drawable.button_login_odnoklassniki;
        if (providerKey.equals("openid")) rid = R.drawable.button_login_openid;
        if (providerKey.equals("paypal")) rid = R.drawable.button_login_paypal;
        if (providerKey.equals("reddit")) rid = R.drawable.button_login_reddit;
        if (providerKey.equals("skyrock")) rid = R.drawable.button_login_skyrock;
        if (providerKey.equals("stackexchange")) rid = R.drawable.button_login_stackexchange;
        if (providerKey.equals("steam")) rid = R.drawable.button_login_steam;
        if (providerKey.equals("twitch")) rid = R.drawable.button_login_twitch;
        if (providerKey.equals("twitter")) rid = R.drawable.button_login_twitter;
        if (providerKey.equals("vimeo")) rid = R.drawable.button_login_vimeo;
        if (providerKey.equals("vkontakte")) rid = R.drawable.button_login_vkontakte;
        if (providerKey.equals("windowslive")) rid = R.drawable.button_login_windowslive;
        if (providerKey.equals("wordpress")) rid = R.drawable.button_login_wordpress;
        if (providerKey.equals("yahoo")) rid = R.drawable.button_login_yahoo;
        if (providerKey.equals("youtube")) rid = R.drawable.button_login_youtube;

        if (rid != 0) {
            return getResources().getDrawable(rid);
        }
        return null;
    }

    // endregion
}
