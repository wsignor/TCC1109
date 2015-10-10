package com.example.eduardo.tcc.Nutricionista;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eduardo.tcc.R;
import com.example.eduardo.tcc.RecomendacoesAvaliacao.AlimentosPraticas;
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


    private List<ParseObject> queryClientes;
    String objectIdClienteSelecionado = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listitem);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);

        queryClientes = new ArrayList<ParseObject>();
        ParseQuery<ParseObject> innerQuery = ParseQuery.getQuery("_User");
        innerQuery.whereEqualTo("idNutricionista", ParseObject.createWithoutData("_User", ParseUser.getCurrentUser().getObjectId()));
        //innerQuery.whereEqualTo("nutricionista", false);
        ArrayList<ParseObject> listaExibida = new ArrayList<ParseObject>();

        try {
            queryClientes = innerQuery.find();

            for(ParseObject parseObj : queryClientes){
                System.out.println("parseObj: " + parseObj.getObjectId());
                listaExibida.add(parseObj);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        TextView nome_cliente = (TextView) findViewById(R.id.nome_cliente);
        TextView email_cliente = (TextView) findViewById(R.id.email_cliente);
        TextView sexo_cliente = (TextView) findViewById(R.id.sexo_cliente);

        objectIdClienteSelecionado = listaExibida.get(position).getObjectId();
        nome_cliente.setText(listaExibida.get(position).get("nome").toString());
        email_cliente.setText(listaExibida.get(position).get("email").toString());
        sexo_cliente.setText(listaExibida.get(position).get("sexo").toString());

        Button visualizarDadosCliente = (Button)findViewById(R.id.btnVisualizarDadosCliente);
        visualizarDadosCliente.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(ListItemDetail.this, "Puxar mesma avaliação do usuário.", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnAddAlimentos = (Button)findViewById(R.id.btnAddAlimentos);
        btnAddAlimentos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(ListItemDetail.this, "Adicionar alimentos.", Toast.LENGTH_SHORT).show();

                // (String action, Uri uri,Context packageContext, Class<?> cls)
                //Intent novoAlimento = new Intent("", null, ListItemDetail.this, NovoAlimento.class);
                Intent novoAlimento = new Intent(ListItemDetail.this, NovoAlimento.class);

                novoAlimento.putExtra("objectId" , objectIdClienteSelecionado);
                startActivity(novoAlimento);
            }
        });

        Button btnVisualizarDieta  = (Button) findViewById(R.id.btnVisualizarDadosCliente);
        btnVisualizarDieta.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent intent = new Intent();
                intent.setClass(v.getContext(), AlimentosPraticas.class);
                intent.putExtra("idDoencaCliente", "idDoencaClienteDado");
                //intent.putExtra("objectIdClienteSelecionado" , objectIdClienteSelecionado);
                startActivity(intent);
            }
        });

    }
}
