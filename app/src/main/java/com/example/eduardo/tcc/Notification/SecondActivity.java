package com.example.eduardo.tcc.Notification;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.eduardo.tcc.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

//import com.blundell.tut.R;

/**
 * This is the activity that is started when the user presses the notification in the status bar
 /**
 * Created by Wagner on 21/11/2015.
 */
public class SecondActivity extends Activity {

    private TextView txtTituloPratica;
    private TextView txtRecomendacaoPratica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.praticas_random);

        txtTituloPratica = (TextView) findViewById(R.id.txtTituloPratica);
        txtRecomendacaoPratica = (TextView) findViewById(R.id.txtRecomendacaoPratica);


        ParseQuery<ParseObject> queryAvaliacao = ParseQuery.getQuery("Avaliacao");
        queryAvaliacao.whereEqualTo("idUsuario", ParseObject.createWithoutData("_User", ParseUser.getCurrentUser().getObjectId()));
        queryAvaliacao.whereDoesNotExist("dtTermino");


        try {
            ParseQuery<ParseObject> queryDoenca = ParseQuery.getQuery("Doenca");
            queryDoenca.whereEqualTo("idDoenca", ParseObject.createWithoutData("Doenca", queryAvaliacao.getFirst().get("idDoenca").toString()));
            String nomeDoenca = queryDoenca.getFirst().get("nome").toString();

        } catch (ParseException e) {
            e.printStackTrace();
        }







    }

}