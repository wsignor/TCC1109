package com.example.eduardo.tcc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wagner on 03/10/2015.
 */
public class ListItemDetail extends Activity {


    private List<ParseObject> queryClientes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listitem);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);

        queryClientes = new ArrayList<ParseObject>();
        ParseQuery<ParseObject> innerQuery = ParseQuery.getQuery("_User");
        //innerQuery.whereEqualTo("idNutricionista", ParseObject.createWithoutData("_User", ParseUser.getCurrentUser().getObjectId()));
        innerQuery.whereEqualTo("nutricionista", false);
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
        nome_cliente.setText(listaExibida.get(position).get("nome").toString());
        email_cliente.setText(listaExibida.get(position).get("email").toString());
        sexo_cliente.setText(listaExibida.get(position).get("sexo").toString());

    }
}
