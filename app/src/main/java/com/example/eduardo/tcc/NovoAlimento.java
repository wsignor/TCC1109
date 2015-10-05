package com.example.eduardo.tcc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class NovoAlimento extends Activity {

    private Button btnMeusDados;
    private String objectIdClienteSelecionado = "";
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
                Toast.makeText(NovoAlimento.this, "Incluir alimento.", Toast.LENGTH_LONG).show();

                incluirAlimentos();

            }
        });

    }

    private void incluirAlimentos() {
        System.out.println("incluirAlimentos para: " + objectIdClienteSelecionado);
        EditText alimento = (EditText) findViewById(R.id.txtAlimento);
        EditText quantidade = (EditText) findViewById(R.id.txtQuantidade);


        // inserir na tabela de alimentos do usuario
    }
}
