package com.example.eduardo.tcc.Inicio;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eduardo.tcc.CadastroUsuario.DadosUsuario;
import com.example.eduardo.tcc.Email.SendMailActivity;
import com.example.eduardo.tcc.Grafico.Grafico;
import com.example.eduardo.tcc.Notification.Notificacao;
import com.example.eduardo.tcc.Notification.ScheduleClient;
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
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class Inicial extends Activity {

    private Button btnMeusDados;
    private Button btnSair;
    private Button btnNovaAvaliacao;
    private Button btnMinhaAvaliacao;
    private Button btnMeusClientes;
    private Button btnGrafico;
    private Button btnTeste;
    private Button btnNotificacao;
    private Button btnMail;
    private TextView textViewToChange;
    protected ProgressDialog proDialog;
    private ScheduleClient scheduleClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicial);


        // Atribuições
        scheduleClient = new ScheduleClient(this);
        scheduleClient.doBindService();
        textViewToChange = (TextView) findViewById(R.id.txtNomeUsuario);
        textViewToChange.setText(ParseUser.getCurrentUser().getString("nome"));
        btnMeusDados = (Button) findViewById(R.id.btnMeusDados);
        btnMinhaAvaliacao = (Button) findViewById(R.id.btnMinhaAvaliacao);
        btnNovaAvaliacao = (Button) findViewById(R.id.btnNovaAvaliacao);
        btnMeusClientes = (Button) findViewById(R.id.btnMeusClientes);
        btnGrafico = (Button) findViewById(R.id.btnGrafico);
        btnSair = (Button) findViewById(R.id.btnSair);
        btnNotificacao = (Button) findViewById(R.id.btnNotificacao);


        // Tentando cadastrar o aparelho no login
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


        // Tramento para exibição dos botões
        if(CurrentUser.getAvaliacaoTemp() == null && CurrentUser.getAvaliacao() == null){
            btnMinhaAvaliacao.setVisibility(View.INVISIBLE);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)btnNovaAvaliacao.getLayoutParams();
            params.addRule(RelativeLayout.BELOW, R.id.btnMeusDados);
            btnNovaAvaliacao.setLayoutParams(params);
        }

        if(ParseUser.getCurrentUser().get("nutricionista") != null && ParseUser.getCurrentUser().get("nutricionista").equals(false)) {
            btnMeusClientes.setVisibility(View.INVISIBLE);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)btnGrafico.getLayoutParams();
            params.addRule(RelativeLayout.BELOW, R.id.btnNovaAvaliacao);
            params = (RelativeLayout.LayoutParams)btnSair.getLayoutParams();
            params.addRule(RelativeLayout.BELOW, R.id.btnGrafico);
            btnSair.setLayoutParams(params);
        }





        // Eventos dos botões
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
                    takeUserHomepage.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(takeUserHomepage);
                }

                if(CurrentUser.getAvaliacao() != null){
                    Intent intent = new Intent();
                    intent.setClass(v.getContext(), PraticasNutricionais.class);
                    intent.putExtra("idDoenca", CurrentUser.getAvaliacao().getParseObject("idDoenca").getObjectId());
                    intent.putExtra("idAvaliacao", CurrentUser.getAvaliacao().getObjectId());
                    startActivity(intent);
                }
            }
        });

        btnNovaAvaliacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((CurrentUser.getDoencasTemp() != null && CurrentUser.getDoencasTemp().size() > 0) || CurrentUser.getAvaliacao() != null) {

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    Intent takeUserHomepage = new Intent(Inicial.this, FormularioAvaliacao.class);
                                    takeUserHomepage.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    startActivity(takeUserHomepage);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage("Você contém uma avaliação vigente, deseja sobrescreve-la?").setPositiveButton("Sim", dialogClickListener).setNegativeButton("Não", dialogClickListener).show();

                } else {
                    Intent takeUserHomepage = new Intent(Inicial.this, FormularioAvaliacao.class);
                    takeUserHomepage.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(takeUserHomepage);
                }
            }
        });

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
                CurrentUser.finishInstance();
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

        btnNotificacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takeUserToNotif = new Intent(Inicial.this, Notificacao.class);
                startActivity(takeUserToNotif);
                onDateSelectedButtonClick(30,10,2015);
            }
        });

//        btnMail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Intent takeUserToMail = new Intent(Inicial.this, SendMailActivity.class);
//                //startActivity(takeUserToMail);
//
//                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
//                sendIntent.setType("plain/text");
//                sendIntent.setData(Uri.parse("test@gmail.com"));
//                sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
//                sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "test@gmail.com" });
//                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Um cliente seu concluiu a avaliação, veja o resultado.");
//                sendIntent.putExtra(Intent.EXTRA_TEXT, "Corpo do email com as informações necessárias.");
//                startActivity(sendIntent);
//            }
//        });



    }

//    private void scheduleNotification(Notification notification, int delay) {
//
//
//        // make sure there are no pending notifications
//        cancelNotification();
//
//
//        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
//        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
//        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        long futureInMillis = SystemClock.elapsedRealtime() + delay;
//        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
//    }

//    private Notification getNotification(String content) {
//        Notification.Builder builder = new Notification.Builder(this);
//        builder.setContentTitle("Prática recomendada:");
//        builder.setContentText(content);
//        builder.setSmallIcon(R.drawable.ic_launcher);
//        return builder.build();
//    }
//
//    private void cancelNotification(){
//        String ns = Context.NOTIFICATION_SERVICE;
//        NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
//        nMgr.cancelAll();
//    }
//
//    private void enviarNotificacoes() {
//        //scheduleClient = new ScheduleClient(this);
//
//        int dia = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
//        int mes = Calendar.getInstance().getTime().getMonth();
//        int ano = Calendar.getInstance().get(Calendar.YEAR);
//
//        System.out.print("dia: " + dia + " - mes: " + mes + " - ano: " + ano);
//
//        for(int i = 0 ; i <= 14 ; i++){
//            //System.out.println("MAXIMUM: " + Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));
//
//            if(dia > Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)){
//                dia = 1;
//                mes = mes + 1;
//            }
//
//            if(mes > 12){
//                mes = 1;
//                ano = ano + 1;
//            }
//
//            onDateSelectedButtonClick(dia,mes,ano);
//
//            dia++;
//        }
//
//
//    }

    /**
     * This is the onClick called from the method above
     */
    public void onDateSelectedButtonClick(int dia, int mes, int ano){
        // Get the date from our datepicker
        int day = 26;//picker.getDayOfMonth();
        int month = 11;//picker.getMonth();
        int year = 2015;//picker.getYear();


        // Create a new calendar set to the date chosen
        // we set the time to midnight (i.e. the first minute of that day)
        Calendar c = Calendar.getInstance();
        //c.set(c.getTime().getYear(), c.getTime().getMonth(), c.getTime().getDay());

        c.set(ano, mes, dia);


        System.out.println("current time: " + c.getTime().toString());

//        c.set(Calendar.HOUR_OF_DAY, c.getTime().getHours());
//        c.set(Calendar.MINUTE, c.getTime().getMinutes());
//        c.set(Calendar.SECOND, c.getTime().getSeconds()+20);

//        c.set(Calendar.HOUR_OF_DAY, 0);
//        c.set(Calendar.MINUTE, 0);
//        c.set(Calendar.SECOND, 0);

        System.out.println(c.toString());
        // Ask our service to set an alarm for that date, this activity talks to the client that talks to the service
        scheduleClient.setAlarmForNotification(c);
        // Notify the user what they just did
        //Toast.makeText(this, "Notification set for: " + day + "/" + (month + 1) + "/" + year, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Notification set for: " + dia + "/" + (mes + 1) + "/" + ano, Toast.LENGTH_SHORT).show();
    }
}
