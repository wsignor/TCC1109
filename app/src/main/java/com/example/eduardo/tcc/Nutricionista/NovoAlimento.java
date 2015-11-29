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

import com.example.eduardo.tcc.CadastroUsuario.DadosUsuario;
import com.example.eduardo.tcc.Inicio.Inicial;
import com.example.eduardo.tcc.R;
import com.example.eduardo.tcc.Util.NotificationPublisher;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONObject;

import java.io.IOException;


public class NovoAlimento extends Activity {

    private Button btnMeusDados;
    private String objectIdClienteSelecionado = "";
    private String idAvaliacao;
    private String idCliente;
    ParseObject novoAlimentoParse;
    private static final String PUSH_URL = "https://api.parse.com/1/push";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.novo_alimento);

        idAvaliacao = getIntent().getExtras().getString("idAvaliacao");
        idCliente = getIntent().getExtras().getString("idCliente");

        Button btnIncluirAlimentos = (Button)findViewById(R.id.btnIncluirAlimento);
        btnIncluirAlimentos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
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
        EditText alimento = (EditText) findViewById(R.id.txtAlimento);
        EditText quantidade = (EditText) findViewById(R.id.txtQuantidade);
        EditText periodicidade = (EditText) findViewById(R.id.txtPeriodicidade);



        novoAlimentoParse = new ParseObject("AlimentoAvaliacao");
        novoAlimentoParse.put("idAvaliacao", ParseObject.createWithoutData("Avaliacao", idAvaliacao));
        novoAlimentoParse.put("descricaoAlimento", alimento.getText().toString());
        novoAlimentoParse.put("quantidade", quantidade.getText().toString());
        novoAlimentoParse.put("periodicidade", periodicidade.getText().toString());

        try {

            novoAlimentoParse.save();

            ParsePush push = new ParsePush();
            //push.setChannel(ParseUser.getCurrentUser().getObjectId());
            System.out.println("ParseUser.getCurrentUser().getObjectId() - set channel: " + ParseUser.getCurrentUser().getObjectId());
            System.out.println("object id - channel - cliente selecionado: " + objectIdClienteSelecionado);
            // depois que o cliente estiver configurado
            push.setChannel(idCliente);
            push.setMessage("Seu nutricionista adicionou novos alimentos à sua dieta, confira!");



//            JSONObject json = new JSONObject();
//            json.put("nome", "dudu");
//            json.put("idade", "24");
//            json.put("alert", "Seu nutricionista adicionou novos alimentos à sua dieta, confira!");
            //json.put("",ParseUser.getCurrentUser().getObjectId());

            //push.setData(json);

            push.sendInBackground();

            // notification

            //scheduleNotification(getNotification("1 second delay"), 1000);

            // PendingIntent pendingIntent = PendingIntent.getActivity(this.getApplicationContext(), 0, getIntent(), PendingIntent.FLAG_CANCEL_CURRENT);



        } catch (Exception e) {
            e.printStackTrace();
        }

        finish();


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
