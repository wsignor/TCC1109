package com.example.eduardo.tcc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Eduardo on 16/09/2015.
 */
public class FormularioAvaliacao extends Activity {

    ParseObject InformacoesMutaveisData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario_avaliacao);

        Spinner spinner;
        spinner = (Spinner) findViewById( R.id.spnNivelColesterol );

        ArrayAdapter<CharSequence> adapterColesterol = ArrayAdapter.createFromResource( this, R.array.nivel_array , android.R.layout.simple_spinner_item);
        adapterColesterol.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterColesterol);

        spinner = (Spinner) findViewById( R.id.spnNivelTriglicerideos );

        ArrayAdapter<CharSequence> adapterTriglicerideos = ArrayAdapter.createFromResource( this, R.array.nivel_array , android.R.layout.simple_spinner_item);
        adapterTriglicerideos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterTriglicerideos);

        Button btnSalvar = (Button)findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                salvarInformacoesImutaveis();

                Intent takeUserHomepage = new Intent(FormularioAvaliacao.this, Inicial.class);
                startActivity(takeUserHomepage);
            }
        });

    }

    private void salvarInformacoesImutaveis(){
        Switch isFumante = (Switch) findViewById(R.id.swtFumante);
        Switch isBebidaAlcoolica = (Switch) findViewById(R.id.swtBebidaAlcoolica);
        Switch isSodio = (Switch) findViewById(R.id.swtConsumoSodio);
        Switch isAcucar = (Switch) findViewById(R.id.swtConsumoAcucar);
        Switch isAtividadeFisica = (Switch) findViewById(R.id.swtAtividadeFisica);
        Switch isAnticoncepcional = (Switch) findViewById(R.id.swtAnticoncepcional);
        Switch isMenopausa = (Switch) findViewById(R.id.swtMenopausa);
        Switch isEstressado = (Switch) findViewById(R.id.swtEstressado);
        Switch isDiabetesGestacional = (Switch) findViewById(R.id.swtDiabetesGestacional);
        Switch isCortisona = (Switch) findViewById(R.id.swtUsoCortisona);
        Switch isDiuretico = (Switch) findViewById(R.id.swtDiureticos);
        Switch isBetaBloqueador = (Switch) findViewById(R.id.swtBetaBloqueadores);
        Switch isFilhos = (Switch) findViewById(R.id.swtTeveFilhos);
        Switch isCompanheira = (Switch) findViewById(R.id.swtCompanheira);
        Switch isOvarioPolicistico = (Switch) findViewById(R.id.swtOvarioPolicistico);
        Switch isDislipidemia = (Switch) findViewById(R.id.swtDislipidemia);
        Switch isMicroalbuminuria = (Switch) findViewById(R.id.swtMicroalbuminúria);
        Switch isIntoleranciaGlicose = (Switch) findViewById(R.id.swtIntoleranciaGlicose);
        Switch isIntoleranciaInsulina = (Switch) findViewById(R.id.swtIntoleranciaInsulina);
        Switch isHiperuricemia = (Switch) findViewById(R.id.swtHiperuricemia);
        Switch isProTrombotico = (Switch) findViewById(R.id.swtProTrombotico);
        EditText peso = (EditText) findViewById(R.id.txtPeso);
        Spinner nivelColesterol = (Spinner) findViewById(R.id.spnNivelColesterol);
        Spinner nivelTriglicerideos = (Spinner) findViewById(R.id.spnNivelTriglicerideos);

        InformacoesMutaveisData = new ParseObject("InformacoesMutaveis");
        InformacoesMutaveisData.put("fumante", isFumante.isChecked());
        InformacoesMutaveisData.put("altoConsumoAlcool", isBebidaAlcoolica.isChecked());
        InformacoesMutaveisData.put("praticaAtividadeFisica", isAtividadeFisica.isChecked());
        InformacoesMutaveisData.put("tomaAnticoncepcional", isAnticoncepcional.isChecked());
        InformacoesMutaveisData.put("peso", Double.parseDouble(peso.getText().toString()));
        InformacoesMutaveisData.put("nivelColesterol", nivelColesterol.getSelectedItem());
        InformacoesMutaveisData.put("nivelTriglicerideos", nivelTriglicerideos.getSelectedItem());
        InformacoesMutaveisData.put("altoConsumoSodio", isSodio.isChecked());
        InformacoesMutaveisData.put("altoConsumoAcucar", isAcucar.isChecked());
        InformacoesMutaveisData.put("menopausa", isMenopausa.isChecked());
        InformacoesMutaveisData.put("estressado", isEstressado.isChecked());
        InformacoesMutaveisData.put("diabetesGestacional", isDiabetesGestacional.isChecked());
        InformacoesMutaveisData.put("cortisona", isCortisona.isChecked());
        InformacoesMutaveisData.put("diuretico", isDiuretico.isChecked());
        InformacoesMutaveisData.put("betaBloqueador", isBetaBloqueador.isChecked());
        InformacoesMutaveisData.put("teveFilhos", isFilhos.isChecked());
        InformacoesMutaveisData.put("companheira", isCompanheira.isChecked());
        InformacoesMutaveisData.put("ovarioPolicistico", isOvarioPolicistico.isChecked());
        InformacoesMutaveisData.put("dislipidemia", isDislipidemia.isChecked());
        InformacoesMutaveisData.put("microalbuminuria", isMicroalbuminuria.isChecked());
        InformacoesMutaveisData.put("intoleranciaGlicose", isIntoleranciaGlicose.isChecked());
        InformacoesMutaveisData.put("intoleranciaInsulina", isIntoleranciaInsulina.isChecked());
        InformacoesMutaveisData.put("hiperuricemia", isHiperuricemia.isChecked());
        InformacoesMutaveisData.put("proTrombotico", isProTrombotico.isChecked());




        InformacoesMutaveisData.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                salvarUsuarioInformacao(InformacoesMutaveisData.getObjectId());
            }
        });
    }

    private void salvarUsuarioInformacao(String idInformacaoMutavel){
        ParseObject UsuarioInformacao = new ParseObject("UsuarioInformacao");
        UsuarioInformacao.put("idUsuario", ParseObject.createWithoutData("_User", ParseUser.getCurrentUser().getObjectId()));
        UsuarioInformacao.put("idInformacao", ParseObject.createWithoutData("InformacoesMutaveis", idInformacaoMutavel));

        ParseQuery innerQuery = new ParseQuery("_User");
        innerQuery.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());

        ParseQuery<ParseObject> query = ParseQuery.getQuery("UsuarioInformacao");
        query.whereMatchesQuery("idUsuario", innerQuery);
        query.addDescendingOrder("versao");
        try {
            ParseObject obj = query.getFirst();
            UsuarioInformacao.put("versao", Integer.parseInt(obj.get("versao").toString()) + 1);
        } catch (ParseException e){
            UsuarioInformacao.put("versao", 1);
        }

        UsuarioInformacao.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                realizarAvaliacao();
            }
        });
    }

    private void realizarAvaliacao(){

        Boolean
                hipertensaoFamiliar,
                diabetesFamiliar,
                cardiovascularFamiliar,
                obesidadeFamiliar,
                sindromeFamiliar,
                diabetico,
                hipertenso,
                fumante,
                praticaAtividadeFisica,
                consumoAlcool,
                consumoSodio,
                consumoAcucar;
        Integer
                peso,
                idade;
        Double
                altura,
                calculoIMC;
        String
                sexo,
                raca;

        ParseQuery innerQuery = new ParseQuery("_User");
        innerQuery.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());

        ParseQuery<ParseObject> queryImutaveis = ParseQuery.getQuery("InformacoesImutaveis");
        queryImutaveis.whereMatchesQuery("idUsuario", innerQuery);

        ParseQuery<ParseObject> queryUsuarioInformacao = ParseQuery.getQuery("UsuarioInformacao");
        queryUsuarioInformacao.whereMatchesQuery("idUsuario", innerQuery);
        queryUsuarioInformacao.addDescendingOrder("versao");

        try {

            //Informacoes Mutaveis
            ParseObject informacoesMutaveis = queryUsuarioInformacao.getFirst();
            informacoesMutaveis = informacoesMutaveis.getParseObject("idInformacao").fetch();
            peso = informacoesMutaveis.getInt("peso");
            fumante = informacoesMutaveis.getBoolean("fumante");
            praticaAtividadeFisica = informacoesMutaveis.getBoolean("praticaAtividadeFisica");
            consumoAlcool = informacoesMutaveis.getBoolean("ingereBebidaAlcoolica");
            consumoSodio = informacoesMutaveis.getBoolean("altoConsumoAlcool");
            consumoAcucar = informacoesMutaveis.getBoolean("altoConsumoAcucar");


            //Informacoes do Usuário
            altura = ParseUser.getCurrentUser().getDouble("altura");
            sexo = ParseUser.getCurrentUser().getString("sexo");

            String anoNascimento = ParseUser.getCurrentUser().get("dtNascimento").toString();
            String[] parts = anoNascimento.split("/");
            anoNascimento = parts[2];
            System.out.println("anoNascimento: " + anoNascimento);
            idade = Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(anoNascimento);
            System.out.println("idade: " + idade.toString());

            raca = ParseUser.getCurrentUser().getString("raca");


            //Informacoes Imutaveis
            ParseObject informacoesImutaveis = queryImutaveis.getFirst();
            hipertensaoFamiliar = informacoesImutaveis.getBoolean("hipertensaoFamiliar");
            diabetesFamiliar = informacoesImutaveis.getBoolean("diabetesFamiliar");
            cardiovascularFamiliar = informacoesImutaveis.getBoolean("cardiovascularFamiliar");
            obesidadeFamiliar = informacoesImutaveis.getBoolean("obesidadeFamiliar");
            sindromeFamiliar = informacoesImutaveis.getBoolean("sindromeFamiliar");
            sindromeFamiliar = informacoesImutaveis.getBoolean("sindromeFamiliar");
            diabetico = informacoesImutaveis.getBoolean("diabetico");
            hipertenso = informacoesImutaveis.getBoolean("hipertenso");

            //Outras Informacoes
            calculoIMC = peso / (altura * altura);


        }catch (ParseException e){

        }
    }
}
