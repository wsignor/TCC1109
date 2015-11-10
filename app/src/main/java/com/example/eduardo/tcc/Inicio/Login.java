package com.example.eduardo.tcc.Inicio;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eduardo.tcc.CadastroUsuario.DadosUsuario;
import com.example.eduardo.tcc.Entidades.CurrentUser;
import com.example.eduardo.tcc.R;
import com.example.eduardo.tcc.Util.LoadingUtils;
import com.example.eduardo.tcc.Util.NotificationPublisher;
import com.parse.LogInCallback;
import com.parse.Parse;
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
                                    R.string.login_toast, Toast.LENGTH_SHORT).show();
                            CurrentUser.startInstance();
                            Intent takeUserHomepage = new Intent(Login.this, Inicial.class);
                            startActivity(takeUserHomepage);

                            montarMensagem();

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
                ParseUser.getCurrentUser().logOut();
                Intent createNewAccount = new Intent(Login.this, DadosUsuario.class);
                startActivity(createNewAccount);

            }
        });
    }

    private void montarMensagem(){
        scheduleNotification(getNotification("Login"), 1000);
    }

    private void scheduleNotification(Notification notification, int delay) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_launcher);
        return builder.build();
    }
}
