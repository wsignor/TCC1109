package com.example.eduardo.tcc.Inicio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.eduardo.tcc.Entidades.CurrentUser;
import com.parse.ParseUser;

/**
 * Created by Eduardo on 16/09/2015.
 */

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Parse.initialize(this, "ih17LzHvWd7nsSZ9iQSqo0ymIfXWwYzmMG3sOilj", "PJW3q1jRs1Pll0LxfovghEzEjDiKufTPMUBc8yjH");
//
//        ParseUser.enableAutomaticUser();
//        ParseACL defaultACL = new ParseACL();
//        defaultACL.setPublicReadAccess(true);
//        ParseACL.setDefaultACL(defaultACL, true);
//        ParsePush.subscribeInBackground("", new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if (e != null) {
//                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
//                } else {
//                    Log.e("com.parse.push", "failed to subscribe for push", e);
//                }
//            }
//        });

        if (ParseUser.getCurrentUser().getObjectId() != null){
            System.out.println("MainActivity - getcurruser: " + ParseUser.getCurrentUser().getObjectId());
            CurrentUser.startInstance();
            Intent createNewAccount = new Intent(MainActivity.this, Inicial.class);
            startActivity(createNewAccount);
        }else{
            Intent createNewAccount = new Intent(MainActivity.this, Login.class);
            startActivity(createNewAccount);
        }
    }
}
