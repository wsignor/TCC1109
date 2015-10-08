package com.example.eduardo.tcc;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Wagner on 06/10/2015.
 */
public class LoadingUtils {

    protected static ProgressDialog proDialog;

    public static void startLoading(Context context) {
        proDialog = new ProgressDialog(context);
        proDialog.setMessage("loading...");
        proDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        proDialog.setCancelable(false);
        proDialog.show();
    }

    public static void stopLoading() {
        proDialog.dismiss();
        proDialog = null;
    }

}
