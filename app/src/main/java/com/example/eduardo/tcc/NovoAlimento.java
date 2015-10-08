package com.example.eduardo.tcc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseUser;

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
                Toast.makeText(NovoAlimento.this, "Incluir alimento.", Toast.LENGTH_LONG).show();

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
            // depois que o cliente estiver configurado
            //push.setChannel(objectIdClienteSelecionado);
            push.setMessage("Seu nutricionista adicionou novos alimentos à sua dieta, confira!");
            push.sendInBackground();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
