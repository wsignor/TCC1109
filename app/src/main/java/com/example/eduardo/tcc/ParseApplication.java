package com.example.eduardo.tcc;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.sql.SQLOutput;

/**
 * Created by Wagner on 07/10/2015.
 */
public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // initialization code
        //Parse.initialize(this, "w2akEFYvsfFnLYro0PVaH2phaoK50n97pNuvFV4T",
        //        "CPHtfLdfnNTaFQ98OZgZqeHlDRehgrLEgjbYa9cb");

        Parse.initialize(this, "ih17LzHvWd7nsSZ9iQSqo0ymIfXWwYzmMG3sOilj", "PJW3q1jRs1Pll0LxfovghEzEjDiKufTPMUBc8yjH");

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);

        System.out.println("ParseInstallation.getCurrentInstallation(): " + ParseInstallation.getCurrentInstallation().getObjectId());

        deleteInstallationCache(this);

        //ParseInstallation.getCurrentInstallation().saveInBackground();

        ParsePush.subscribeInBackground(ParseUser.getCurrentUser().getObjectId(), new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }

    public static boolean deleteInstallationCache(Context context) {
        boolean deletedParseFolder = false;
        File cacheDir = context.getCacheDir();
        File parseApp = new File(cacheDir.getParent(),"app_Parse");
        File installationId = new File(parseApp,"installationId");
        File currentInstallation = new File(parseApp,"currentInstallation");
        if(installationId.exists()) {
            deletedParseFolder = deletedParseFolder || installationId.delete();
        }
        if(currentInstallation.exists()) {
            deletedParseFolder = deletedParseFolder && currentInstallation.delete();
        }
        return deletedParseFolder;
    }
}
