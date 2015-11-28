package com.example.eduardo.tcc.Nutricionista;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eduardo.tcc.Entidades.CurrentUser;
import com.example.eduardo.tcc.R;
import com.example.eduardo.tcc.RecomendacoesAvaliacao.PraticasNutricionais;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wagner on 03/10/2015.
 */
public class Descricao_Pratica extends Activity {


    private List<ParseObject> listaPraticas;
    String objectIdClienteSelecionado = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descricao_pratica);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);

        listaPraticas = new ArrayList<ParseObject>();

        String idDoenca = intent.getStringExtra("idDoenca");

        ParseQuery<ParseObject> innerQuery = ParseQuery.getQuery("Doenca");
        innerQuery.whereEqualTo("objectId", idDoenca);

        ParseQuery<com.parse.ParseObject> queryPraticas = ParseQuery.getQuery("DoencaPratica");
        queryPraticas.whereMatchesQuery("idDoenca", innerQuery);
        ArrayList<ParseObject> listaExibida = new ArrayList<ParseObject>();

        try {
            listaPraticas = queryPraticas.find();

            for(ParseObject parseObj : listaPraticas){
                System.out.println("parseObj: " + parseObj.getObjectId());
                listaExibida.add(parseObj);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        TextView descricao = (TextView) findViewById(R.id.descricao);
        TextView descricao_detalhada = (TextView) findViewById(R.id.descricao_detalhada);

        objectIdClienteSelecionado = listaExibida.get(position).getObjectId();
        descricao.setText(String.format(getResources().getString(R.string.descricaoPratica), listaExibida.get(position).get("descricao").toString()));
        Object descricao_completa = listaExibida.get(position).get("descricaoCompleta");
        if(descricao_completa != null) {
            descricao_detalhada.setText(descricao_completa.toString());
        }

    }
}
