package com.example.eduardo.tcc.Inicio;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.eduardo.tcc.CadastroUsuario.DadosUsuario;
import com.example.eduardo.tcc.Entidades.Avaliacao;
import com.example.eduardo.tcc.Grafico.Grafico;
import com.example.eduardo.tcc.Notification.ScheduleClient;
import com.example.eduardo.tcc.Nutricionista.ClientesNutricionista;
import com.example.eduardo.tcc.Entidades.CurrentUser;
import com.example.eduardo.tcc.Avaliacao.FormularioAvaliacao;
import com.example.eduardo.tcc.R;
import com.example.eduardo.tcc.Avaliacao.ResultadoAvaliacao;
import com.example.eduardo.tcc.RecomendacoesAvaliacao.PraticasNutricionais;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class Inicial extends Activity {

    private Button btnMeusDados;
    private Button btnSair;
    private Button btnNovaAvaliacao;
    private Button btnMinhaAvaliacao;
    private Button btnMeusClientes;
    private Button btnGrafico;
    private TextView textViewToChange;
    protected ProgressDialog proDialog;
    //private ScheduleClient scheduleClient;

    @Override
    protected void onResume() {
        super.onResume();
        if(CurrentUser.getAvaliacaoTemp() == null && CurrentUser.getAvaliacao() == null){
            btnMinhaAvaliacao.setVisibility(View.INVISIBLE);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)btnNovaAvaliacao.getLayoutParams();
            params.addRule(RelativeLayout.BELOW, R.id.btnMeusDados);
            btnNovaAvaliacao.setLayoutParams(params);
        } else {
            btnMinhaAvaliacao.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)btnNovaAvaliacao.getLayoutParams();
            params.addRule(RelativeLayout.BELOW, R.id.btnMinhaAvaliacao);
            btnNovaAvaliacao.setLayoutParams(params);

            params = (RelativeLayout.LayoutParams)btnGrafico.getLayoutParams();
            params.addRule(RelativeLayout.BELOW, R.id.btnNovaAvaliacao);
            btnGrafico.setLayoutParams(params);

            params = (RelativeLayout.LayoutParams)btnSair.getLayoutParams();
            params.addRule(RelativeLayout.BELOW, R.id.btnGrafico);
            btnSair.setLayoutParams(params);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicial);

        if(CurrentUser.getAvaliacao() != null){
            if(!Avaliacao.getInstance().validarVigenciaAvaliacao()){
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Sua avaliação expirou. Crie uma nova avaliação para manter o seu acompanhamento diário!").setPositiveButton("Ok", dialogClickListener).show();

            }

        }

        // Atribuições
        //scheduleClient = new ScheduleClient(this);
        //scheduleClient.doBindService();
        textViewToChange = (TextView) findViewById(R.id.txtNomeUsuario);
        textViewToChange.setText(ParseUser.getCurrentUser().getString("nome"));
        btnMeusDados = (Button) findViewById(R.id.btnMeusDados);
        btnMinhaAvaliacao = (Button) findViewById(R.id.btnMinhaAvaliacao);
        btnNovaAvaliacao = (Button) findViewById(R.id.btnNovaAvaliacao);
        btnMeusClientes = (Button) findViewById(R.id.btnMeusClientes);
        btnGrafico = (Button) findViewById(R.id.btnGrafico);
        btnSair = (Button) findViewById(R.id.btnSair);


        // Tentando cadastrar o aparelho no login
        System.out.println("Inicial - oncreate - ParseUser.getCurrentUser().getObjectId(): " + ParseUser.getCurrentUser().getObjectId());
        ParsePush.subscribeInBackground(ParseUser.getCurrentUser().getObjectId(), new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });
        ParseInstallation.getCurrentInstallation().put("user", ParseUser.getCurrentUser());
        ParseInstallation.getCurrentInstallation().saveInBackground();


        // Tramento para exibição dos botões
        if(CurrentUser.getAvaliacaoTemp() == null && CurrentUser.getAvaliacao() == null){
            btnMinhaAvaliacao.setVisibility(View.INVISIBLE);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)btnNovaAvaliacao.getLayoutParams();
            params.addRule(RelativeLayout.BELOW, R.id.btnMeusDados);
            btnNovaAvaliacao.setLayoutParams(params);
        }

        if(ParseUser.getCurrentUser().get("nutricionista") != null && ParseUser.getCurrentUser().get("nutricionista").equals(false)) {
            btnMeusClientes.setVisibility(View.INVISIBLE);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)btnGrafico.getLayoutParams();
            params.addRule(RelativeLayout.BELOW, R.id.btnNovaAvaliacao);
            params = (RelativeLayout.LayoutParams)btnSair.getLayoutParams();
            params.addRule(RelativeLayout.BELOW, R.id.btnGrafico);
            btnSair.setLayoutParams(params);
        }





        // Eventos dos botões
        btnMeusDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takeUserEditarDados = new Intent(Inicial.this, DadosUsuario.class);
                startActivity(takeUserEditarDados);
            }
        });

        btnMinhaAvaliacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CurrentUser.getDoencasTemp() != null && CurrentUser.getDoencasTemp().size() > 0) {
                    Intent takeUserHomepage = new Intent(Inicial.this, ResultadoAvaliacao.class);
                    takeUserHomepage.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(takeUserHomepage);
                }

                if(CurrentUser.getAvaliacao() != null){
                    Intent intent = new Intent();
                    intent.setClass(v.getContext(), PraticasNutricionais.class);
                    intent.putExtra("idDoenca", CurrentUser.getAvaliacao().getParseObject("idDoenca").getObjectId());
                    intent.putExtra("idAvaliacao", CurrentUser.getAvaliacao().getObjectId());
                    startActivity(intent);
                }
            }
        });

        btnNovaAvaliacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((CurrentUser.getDoencasTemp() != null && CurrentUser.getDoencasTemp().size() > 0) || CurrentUser.getAvaliacao() != null) {

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    Intent takeUserHomepage = new Intent(Inicial.this, FormularioAvaliacao.class);
                                    takeUserHomepage.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    startActivity(takeUserHomepage);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage("Você contém uma avaliação vigente, deseja sobrescreve-la?").setPositiveButton("Sim", dialogClickListener).setNegativeButton("Não", dialogClickListener).show();

                } else {
                    Intent takeUserHomepage = new Intent(Inicial.this, FormularioAvaliacao.class);
                    takeUserHomepage.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(takeUserHomepage);
                }
            }
        });

        btnMeusClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takeNutriClientsPage = new Intent(Inicial.this, ClientesNutricionista.class);
                startActivity(takeNutriClientsPage);
            }
        });

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                currentUser.logOut();
                CurrentUser.finishInstance();
                Intent takeUserHomepage = new Intent(Inicial.this, Login.class);
                startActivity(takeUserHomepage);
            }
        });

        btnGrafico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takeUserToGraphics = new Intent(Inicial.this, Grafico.class);
                startActivity(takeUserToGraphics);
            }
        });


    }
}
