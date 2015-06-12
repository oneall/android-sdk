package com.oneall.oneallsdk;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * dialog used to get additional information about the provider from the user before login
 */
public class UserInputDialog extends DialogFragment {

    // region Helper classes
    public interface DialogListener {
        void onCancel();
        void onAccept(String userInput);
    }
    // endregion

    // region Constants

    public final static String ARGUMENT_PROVIDER_NAME = "provider";
    public final static String ARGUMENT_USER_INPUT_TYPE = "user_input_type";

    // endregion

    // region Properties

    private DialogListener listener;

    // endregion

    public UserInputDialog() {
    }

    public void setListener(DialogListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.user_input_dialog, container);

        getDialog().setTitle(getArguments().getString(ARGUMENT_PROVIDER_NAME));

        String message = String.format(
                getActivity().getResources().getString(R.string.login_user_input_message),
                getArguments().getString(ARGUMENT_PROVIDER_NAME),
                getArguments().getString(ARGUMENT_USER_INPUT_TYPE));


        ((TextView) view.findViewById(R.id.edit_user_input_message)).setText(message);

        view.findViewById(R.id.edit_user_input_button_ok).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            String text = ((EditText) view.findViewById(R.id.edit_user_input_text))
                                    .getText().toString();
                            listener.onAccept(text);
                        }
                        dismiss();
                    }
                }
        );

        view.findViewById(R.id.edit_user_input_button_cancel).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onCancel();
                        }
                        dismiss();
                    }
                }
        );

        return view;
    }
}