package com.example.eduardo.tcc.Inicio;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.eduardo.tcc.CadastroUsuario.DadosUsuario;
import com.example.eduardo.tcc.Grafico.Grafico;
import com.example.eduardo.tcc.Nutricionista.ClientesNutricionista;
import com.example.eduardo.tcc.Entidades.CurrentUser;
import com.example.eduardo.tcc.Avaliacao.FormularioAvaliacao;
import com.example.eduardo.tcc.Push.PraticaRandom;
import com.example.eduardo.tcc.R;
import com.example.eduardo.tcc.Avaliacao.ResultadoAvaliacao;
import com.example.eduardo.tcc.Util.NotificationPublisher;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.example.eduardo.tcc.RecomendacoesAvaliacao.PraticasNutricionais;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class Inicial extends Activity {

    private Button btnMeusDados;
    private Button btnSair;
    private Button btnNovaAvaliacao;
    private Button btnMinhaAvaliacao;
    private Button btnMeusClientes;
    private Button btnGrafico;
    private Button btnTeste;
    private TextView textViewToChange;
    protected ProgressDialog proDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicial);

        // tentando cadastrar o aparelho no login
        System.out.println("Inicial - oncreate - ParseUser.getCurrentUser().getObjectId(): " + ParseUser.getCurrentUser().getObjectId());
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
        ParseInstallation.getCurrentInstallation().put("user", ParseUser.getCurrentUser());
        ParseInstallation.getCurrentInstallation().saveInBackground();
        //ParsePush.subscribeInBackground(ParseUser.getCurrentUser().getObjectId());

        scheduleNotification(getNotification("Redução de alimentos com sódio."), 1000);


        textViewToChange = (TextView) findViewById(R.id.txtNomeUsuario);
        textViewToChange.setText(ParseUser.getCurrentUser().getString("nome"));

        btnMeusDados = (Button) findViewById(R.id.btnMeusDados);
        btnMinhaAvaliacao = (Button) findViewById(R.id.btnMinhaAvaliacao);
        btnNovaAvaliacao = (Button) findViewById(R.id.btnNovaAvaliacao);
        btnMeusClientes = (Button) findViewById(R.id.btnMeusClientes);
        btnGrafico = (Button) findViewById(R.id.btnGrafico);
        btnTeste = (Button) findViewById(R.id.btnTeste);
        btnSair = (Button) findViewById(R.id.btnSair);

        btnTeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takeUserEditarDados = new Intent(Inicial.this, PraticasNutricionais.class);
                startActivity(takeUserEditarDados);
            }
        });

        btnMeusDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takeUserEditarDados = new Intent(Inicial.this, DadosUsuario.class);
                startActivity(takeUserEditarDados);
            }
        });


        btnMinhaAvaliacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CurrentUser.getDoencasTemp() != null && CurrentUser.getDoencasTemp().size() > 0) {
                    Intent takeUserHomepage = new Intent(Inicial.this, ResultadoAvaliacao.class);
                    startActivity(takeUserHomepage);
                }
            }
        });

        if(CurrentUser.getAvaliacaoTemp() == null){
            btnMinhaAvaliacao.setVisibility(View.INVISIBLE);

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)btnNovaAvaliacao.getLayoutParams();
            params.addRule(RelativeLayout.BELOW, R.id.btnMeusDados);
            btnNovaAvaliacao.setLayoutParams(params);
        }


        btnNovaAvaliacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CurrentUser.getDoencasTemp() != null && CurrentUser.getDoencasTemp().size() > 0) {

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    Intent takeUserHomepage = new Intent(Inicial.this, FormularioAvaliacao.class);
                                    startActivity(takeUserHomepage);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage("Você contém uma avaliação vigente, deseja sobrescreve-la?").setPositiveButton("Sim", dialogClickListener).setNegativeButton("Não", dialogClickListener).show();

                } else {
                    Intent takeUserHomepage = new Intent(Inicial.this, FormularioAvaliacao.class);
                    startActivity(takeUserHomepage);
                }
            }
        });



        if(ParseUser.getCurrentUser().get("nutricionista") != null && ParseUser.getCurrentUser().get("nutricionista").equals(false)) {
            btnMeusClientes.setVisibility(View.INVISIBLE);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)btnSair.getLayoutParams();
            params.addRule(RelativeLayout.BELOW, R.id.btnNovaAvaliacao);
            btnSair.setLayoutParams(params);
        }

        btnMeusClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent takeNutriClientsPage = new Intent(Inicial.this, ClientesNutricionista.class);
                startActivity(takeNutriClientsPage);

            }
        });

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                currentUser.logOut();
                Intent takeUserHomepage = new Intent(Inicial.this, Login.class);
                startActivity(takeUserHomepage);
            }
        });


        btnGrafico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takeUserToGraphics = new Intent(Inicial.this, Grafico.class);
                startActivity(takeUserToGraphics);

            }
        });

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
        builder.setContentTitle("Prática recomendada:");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_launcher);
        return builder.build();
    }
}
