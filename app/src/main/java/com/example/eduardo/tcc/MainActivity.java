package com.example.eduardo.tcc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by Eduardo on 16/09/2015.
 */

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Parse.initialize(this, "ih17LzHvWd7nsSZ9iQSqo0ymIfXWwYzmMG3sOilj", "PJW3q1jRs1Pll0LxfovghEzEjDiKufTPMUBc8yjH");

        if (ParseUser.getCurrentUser() != null){
            CurrentUser.initCurrentUser();
            Intent createNewAccount = new Intent(MainActivity.this, Inicial.class);
            startActivity(createNewAccount);
        }else{
            Intent createNewAccount = new Intent(MainActivity.this, Login.class);
            startActivity(createNewAccount);
        }
    }
}
