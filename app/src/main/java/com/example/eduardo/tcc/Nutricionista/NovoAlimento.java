package com.example.eduardo.tcc.Nutricionista;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eduardo.tcc.R;
import com.example.eduardo.tcc.Util.NotificationPublisher;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseUser;

import org.json.JSONObject;

import java.io.IOException;


public class NovoAlimento extends Activity {

    private Button btnMeusDados;
    private String objectIdClienteSelecionado = "";
    ParseObject novoAlimentoParse;
    private static final String PUSH_URL = "https://api.parse.com/1/push";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.novo_alimento);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            objectIdClienteSelecionado = extras.getString("objectId");
            System.out.println("objectId cliente selecionado: " + objectIdClienteSelecionado);
        }


        final Button btnIncluirAlimentos = (Button)findViewById(R.id.btnIncluirAlimento);
        btnIncluirAlimentos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //LoadingUtils.startLoading(NovoAlimento.this);
                Toast.makeText(NovoAlimento.this, "Incluir alimento.", Toast.LENGTH_SHORT).show();

                try {
                    incluirAlimentos();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private void incluirAlimentos() throws IOException {
        //LoadingUtils.stopLoading();
        System.out.println("incluirAlimentos para: " + objectIdClienteSelecionado);
        EditText alimento = (EditText) findViewById(R.id.txtAlimento);
        EditText quantidade = (EditText) findViewById(R.id.txtQuantidade);

        /*
        novoAlimentoParse = new ParseObject("TABELA_ALIMENTOS");
        novoAlimentoParse.put("objectId", "");
        novoAlimentoParse.put("idUsuario", ParseObject.createWithoutData("_User", objectIdClienteSelecionado));
        novoAlimentoParse.put("alimento", "");
        novoAlimentoParse.put("quantidade", "");
        */


        try {


            ParsePush push = new ParsePush();
            push.setChannel(ParseUser.getCurrentUser().getObjectId());
            System.out.println("ParseUser.getCurrentUser().getObjectId() - set channel: " + ParseUser.getCurrentUser().getObjectId());
            // depois que o cliente estiver configurado
            //push.setChannel(objectIdClienteSelecionado);
            push.setMessage("Seu nutricionista adicionou novos alimentos à sua dieta, confira!");



            JSONObject json = new JSONObject();
            json.put("nome", "dudu");
            json.put("idade", "24");
            json.put("alert", "Seu nutricionista adicionou novos alimentos à sua dieta, confira!");
            //json.put("",ParseUser.getCurrentUser().getObjectId());

            //push.setData(json);

            push.sendInBackground();

            // notification

            scheduleNotification(getNotification("1 second delay"), 1000);

            // PendingIntent pendingIntent = PendingIntent.getActivity(this.getApplicationContext(), 0, getIntent(), PendingIntent.FLAG_CANCEL_CURRENT);



        } catch (Exception e) {
            e.printStackTrace();
        }

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
