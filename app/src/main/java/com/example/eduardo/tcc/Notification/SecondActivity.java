package com.example.eduardo.tcc.Notification;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.eduardo.tcc.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

        Random random = new Random();


        ParseQuery<ParseObject> queryAvaliacao = ParseQuery.getQuery("Avaliacao");
        queryAvaliacao.whereEqualTo("idUsuario", ParseObject.createWithoutData("_User", ParseUser.getCurrentUser().getObjectId()));
        queryAvaliacao.whereDoesNotExist("dtTermino");


        try {
            ParseQuery<ParseObject> queryDoenca = ParseQuery.getQuery("Doenca");
            System.out.println("id doenca - " + queryAvaliacao.getFirst().getParseObject("idDoenca").getObjectId());
            queryDoenca.whereEqualTo("objectId", queryAvaliacao.getFirst().getParseObject("idDoenca").getObjectId());
            System.out.println("nome doenca - " + queryDoenca.getFirst().get("nome").toString());
            String nomeDoenca = queryDoenca.getFirst().get("nome").toString();


            ParseQuery<ParseObject> queryPraticas = ParseQuery.getQuery("DoencaPratica");
            queryPraticas.whereEqualTo("idDoenca", ParseObject.createWithoutData("Doenca", queryAvaliacao.getFirst().getParseObject("idDoenca").getObjectId()));

            List<ParseObject> listaPraticas = new ArrayList<ParseObject>();
            listaPraticas = queryPraticas.find();


            int randomNumero = 0;

            randomNumero = random.nextInt(listaPraticas.size());

            String descricao = listaPraticas.get(randomNumero).get("descricao").toString();
            String descricaoCompleta = listaPraticas.get(randomNumero).get("descricaoCompleta").toString();

            System.out.println("desc pratica: " + descricao + " - desc completa: "+ descricaoCompleta);

            txtTituloPratica.setText(descricao);
            txtRecomendacaoPratica.setText(descricaoCompleta);




        } catch (ParseException e) {
            e.printStackTrace();
        }







    }

}