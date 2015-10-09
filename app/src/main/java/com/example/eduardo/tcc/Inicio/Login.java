package com.example.eduardo.tcc.Inicio;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eduardo.tcc.CadastroUsuario.DadosUsuario;
import com.example.eduardo.tcc.Entidades.CurrentUser;
import com.example.eduardo.tcc.R;
import com.example.eduardo.tcc.Util.LoadingUtils;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class Login extends Activity {

    private EditText userLogin;
    private EditText userPassword;
    private Button loginButton;
    private Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        userLogin = (EditText) findViewById(R.id.loginEditText);
        userPassword = (EditText) findViewById(R.id.passwordEditText);
        loginButton = (Button) findViewById(R.id.loginButton);
        createButton = (Button) findViewById(R.id.createButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = userLogin.getText().toString().trim();
                String password = userPassword.getText().toString().trim();



                ParseUser.logInInBackground(login, password, new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {
                        if (parseUser != null) {
                            LoadingUtils.startLoading(Login.this);
                            Toast.makeText(Login.this,
                                    R.string.login_toast, Toast.LENGTH_LONG).show();
                            CurrentUser.startInstance();
                            Intent takeUserHomepage = new Intent(Login.this, Inicial.class);
                            startActivity(takeUserHomepage);


                            // tentando cadastrar o aparelho no login
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
                            //ParsePush.subscribeInBackground(ParseUser.getCurrentUser().getObjectId());




                            LoadingUtils.stopLoading();

                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                            builder.setMessage(e.getMessage()).setTitle(R.string.oops_title).
                                    setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });

            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent createNewAccount = new Intent(Login.this, DadosUsuario.class);
                startActivity(createNewAccount);

            }
        });
    }
}
