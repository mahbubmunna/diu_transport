package com.moonssoft.diubus;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by MoonS on 11-03-17.
 */

public class ShowDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new  AlertDialog.Builder(getActivity());

        Bundle args = getArguments();
        String body = args.getString("body");
        String title = args.getString("title");
        builder.setMessage(body);
        builder.setTitle(title);

        return builder.create();
    }

    static void setDialog(ShowDialogFragment fragment, String title, String body){
        Bundle args = new Bundle();
        args.putString("body", body);
        args.putString("title", title);
        fragment.setArguments(args);
    }



}
