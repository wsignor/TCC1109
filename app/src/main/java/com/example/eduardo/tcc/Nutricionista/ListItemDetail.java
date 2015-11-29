package com.example.eduardo.tcc.Nutricionista;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
public class ListItemDetail extends Activity {


    private ParseObject cliente;
    String objectIdClienteSelecionado = "";
    private ParseObject avaliacao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_cliente);
        buscaInformacoesCliente();

        Button btnAddAlimentos = (Button)findViewById(R.id.btnAddAlimentos);
        btnAddAlimentos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent novoAlimento = new Intent(ListItemDetail.this, NovoAlimento.class);

                novoAlimento.putExtra("idAvaliacao" , avaliacao.getObjectId());
                novoAlimento.putExtra("idCliente" , cliente.getObjectId());
                startActivity(novoAlimento);
            }
        });

        Button btnVisualizarDieta  = (Button) findViewById(R.id.btnVisualizarDadosCliente);
        btnVisualizarDieta.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent intent = new Intent();
                intent.setClass(v.getContext(), PraticasNutricionais.class);
                intent.putExtra("idDoenca", avaliacao.getParseObject("idDoenca").getObjectId());
                intent.putExtra("idAvaliacao", avaliacao.getObjectId());
                startActivity(intent);
            }
        });

    }

    private void buscaInformacoesCliente(){
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        String emailCliente = intent.getStringExtra("email");

        ParseQuery<ParseObject> innerQuery = ParseQuery.getQuery("_User");
        innerQuery.whereEqualTo("email", emailCliente);

        try {
            cliente = innerQuery.getFirst();

            TextView nome_cliente = (TextView) findViewById(R.id.nome_cliente);
            TextView email_cliente = (TextView) findViewById(R.id.email_cliente);
            TextView sexo_cliente = (TextView) findViewById(R.id.sexo_cliente);

            nome_cliente.setText(cliente.getString("nome"));
            email_cliente.setText(emailCliente);
            sexo_cliente.setText(cliente.getString("sexo"));

            ParseQuery<ParseObject> queryAvaliacao = ParseQuery.getQuery("Avaliacao");
            queryAvaliacao.whereMatchesQuery("idUsuario", innerQuery);
            queryAvaliacao.whereDoesNotExist("dtTermino");

            try{
                avaliacao = queryAvaliacao.getFirst();
            }catch (ParseException exp){

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
