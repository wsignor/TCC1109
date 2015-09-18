package com.example.eduardo.tcc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

/**
 * Created by Eduardo on 14/08/2015.
 */
public class DadosUsuario extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    ParseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dados_usuario);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        ViewPagerAdpter_DadosPessoais viewPagerAdpterDadosPessoais = new ViewPagerAdpter_DadosPessoais(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdpterDadosPessoais);


        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        Button btnSalvar = (Button)findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Dados Pessoais
                EditText nome = (EditText) findViewById(R.id.textNome);
                final EditText login = (EditText) findViewById(R.id.txtLogin);
                EditText senha = (EditText) findViewById(R.id.txtPassword);
                Spinner sexo = (Spinner) findViewById(R.id.spnSexo);
                EditText altura = (EditText) findViewById(R.id.textAltura);
                EditText dataNascimento = (EditText) findViewById(R.id.textDtNascimento);
                Spinner raca = (Spinner) findViewById(R.id.spnRaca);
                EditText email = (EditText) findViewById(R.id.textEmail);
                EditText emailNutricionista = (EditText) findViewById(R.id.textEmailNutri);
                Switch eNutricionista = (Switch) findViewById(R.id.swtNutricionista);

                if(ParseUser.getCurrentUser() == null) {
                    user = new ParseUser();
                } else {
                    user = ParseUser.getCurrentUser();
                }
                user.setUsername(login.getText().toString());
                user.setPassword(senha.getText().toString());
                user.setEmail(email.getText().toString());
                user.put("nome", nome.getText().toString());
                user.put("sexo", sexo.getSelectedItem());
                user.put("altura", Double.parseDouble(altura.getText().toString()));
                user.put("dtNascimento", dataNascimento.getText().toString());
                user.put("raca", raca.getSelectedItem().toString());
                user.put("email", email.getText().toString());
                user.put("nutricionista", eNutricionista.isChecked());

                if(ParseUser.getCurrentUser() == null) {
                    registrarUsuario(emailNutricionista.getText().toString());
                }else {
                    salvarUsuario(emailNutricionista.getText().toString());
                }
            }
        });

    }

    private void registrarUsuario(String emailNutricinista){

        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
        query.whereEqualTo("nutricionista", true);
        query.whereEqualTo("email", emailNutricinista);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, com.parse.ParseException e) {
                if (object == null) {
                    Log.d("score", "The getFirst request failed.");
                } else {
                    Log.d("score", "Retrieved the object.");
                    System.out.println("object: " + object.getObjectId().toString());
                    user.put("idNutricionista", ParseObject.createWithoutData("_User", object.getObjectId().toString()));
                }

                user.signUpInBackground(new SignUpCallback() {
                    public void done(com.parse.ParseException e) {
                        if (e == null) {
                            System.out.println("Usuário inserido com sucesso!");
                            cadastraInformacoesImutaveis(user.getObjectId());

                            login();

                        } else {
                            System.out.println("Erro na inserção do User, message: " + e.getMessage());
                        }
                    }
                });

            }
        });
    }

    private void cadastraInformacoesImutaveis(String idUsuario){

        Switch hipertensaoFamiliar = (Switch) findViewById(R.id.swtHipertensaoFamilia);
        Switch diabetesFamiliar = (Switch) findViewById(R.id.swtDiabetesFamilia);
        Switch cardiovascularFamiliar = (Switch) findViewById(R.id.swtCardiovascularFamilia);
        Switch obesidadeFamiliar = (Switch) findViewById(R.id.swtObesidadeFamilia);
        Switch sindromeFamiliar = (Switch) findViewById(R.id.swtSindromeMetabolicaFamilia);
        Switch hipertenso = (Switch) findViewById(R.id.swtHipertenso);
        Switch diabetico = (Switch) findViewById(R.id.swtDiabetico);

        ParseObject InformacoesImutaveisData = new ParseObject("InformacoesImutaveis");
        InformacoesImutaveisData.put("hipertensaoFamiliar", hipertensaoFamiliar.isChecked());
        InformacoesImutaveisData.put("diabetesFamiliar", diabetesFamiliar.isChecked());
        InformacoesImutaveisData.put("cardiovascularFamiliar", cardiovascularFamiliar.isChecked());
        InformacoesImutaveisData.put("obesidadeFamiliar", obesidadeFamiliar.isChecked());
        InformacoesImutaveisData.put("sindromeFamiliar", sindromeFamiliar.isChecked());
        InformacoesImutaveisData.put("hipertenso", hipertenso.isChecked());
        InformacoesImutaveisData.put("diabetico", diabetico.isChecked());
        InformacoesImutaveisData.put("idUsuario", ParseObject.createWithoutData("_User", idUsuario));

        InformacoesImutaveisData.saveInBackground();
    }

    private void login(){

        EditText senha = (EditText) findViewById(R.id.txtPassword);

        try {
            ParseUser.logIn(user.getUsername(), senha.getText().toString());
            Toast.makeText(DadosUsuario.this,
                    R.string.login_toast, Toast.LENGTH_LONG).show();
            Intent takeUserHomepage = new Intent(DadosUsuario.this, Inicial.class);
            startActivity(takeUserHomepage);
        } catch(ParseException e){

        }
    }

    private void salvarUsuario(String emailNutricinista){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
        query.whereEqualTo("nutricionista", true);
        query.whereEqualTo("email", emailNutricinista);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, com.parse.ParseException e) {
                if (object == null) {
                    Log.d("score", "The getFirst request failed.");
                } else {
                    Log.d("score", "Retrieved the object.");
                    System.out.println("object: " + object.getObjectId().toString());
                    user.put("idNutricionista", ParseObject.createWithoutData("_User", object.getObjectId().toString()));
                }

                user.saveInBackground();

            }
        });
    }
}

