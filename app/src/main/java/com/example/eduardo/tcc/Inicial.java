package com.example.eduardo.tcc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;


public class Inicial extends Activity {

    private Button btnMeusDados;
    private Button btnSair;
    private Button btnNovaAvaliacao;
    private TextView textViewToChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicial);

        textViewToChange = (TextView) findViewById(R.id.txtNomeUsuario);
        textViewToChange.setText(ParseUser.getCurrentUser().getString("nome"));



        btnMeusDados = (Button) findViewById(R.id.btnMeusDados);
        btnMeusDados.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent takeUserEditarDados = new Intent(Inicial.this, DadosUsuario.class);
                startActivity(takeUserEditarDados);
            }
        });

        btnSair = (Button) findViewById(R.id.btnSair);
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseUser currentUser = ParseUser.getCurrentUser();
                currentUser.logOut();
                Intent takeUserHomepage = new Intent(Inicial.this, Login.class);
                startActivity(takeUserHomepage);
            }
        });

        btnNovaAvaliacao = (Button) findViewById(R.id.btnNovaAvaliacao);
        btnNovaAvaliacao.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent takeUserHomepage = new Intent(Inicial.this, FormularioAvaliacao.class);
                startActivity(takeUserHomepage);
            }
        });

    }
}
