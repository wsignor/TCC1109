package com.example.eduardo.tcc.Notification;

import android.app.Activity;
import android.os.Bundle;

import com.example.eduardo.tcc.R;

//import com.blundell.tut.R;

/**
 * This is the activity that is started when the user presses the notification in the status bar
 /**
 * Created by Wagner on 21/11/2015.
 */
public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.praticas_random);
    }

}