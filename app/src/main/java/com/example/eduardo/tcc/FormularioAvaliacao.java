package com.example.eduardo.tcc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Eduardo on 16/09/2015.
 */
public class FormularioAvaliacao extends Activity {

    ParseObject InformacoesMutaveisData;
    protected ProgressDialog proDialog;

    int pontuacaoDiabetes = 0;
    int pontuacaoHipertensao = 0;
    int pontuacaoObesidade = 0;
    int pontuacaoCardiovasculares = 0;
    int pontuacaoSindromeMetabolica = 0;

    Map<Integer, String> ranking;

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


                EditText peso = (EditText) findViewById(R.id.txtPeso);
                if(!peso.getText().toString().isEmpty()) {
                    salvarInformacoesImutaveis();

                }else{
                    Toast.makeText(FormularioAvaliacao.this, R.string.peso_invalido, Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void salvarInformacoesImutaveis(){
        Switch isFumante = (Switch) findViewById(R.id.swtFumante);
        Switch isBebidaAlcoolica = (Switch) findViewById(R.id.swtBebidaAlcoolica);
        Switch isSodio = (Switch) findViewById(R.id.swtConsumoSodio);
        Switch isAcucar = (Switch) findViewById(R.id.swtConsumoAcucar);
        Switch isSedentarismo = (Switch) findViewById(R.id.swtSedentarismo);
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
        InformacoesMutaveisData.put("praticaAtividadeFisica", isSedentarismo.isChecked());
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

    protected void startLoading() {
        proDialog = new ProgressDialog(this);
        proDialog.setMessage("loading...");
        proDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        proDialog.setCancelable(false);
        proDialog.show();
    }

    protected void stopLoading() {
        proDialog.dismiss();
        proDialog = null;
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

        startLoading();
        UsuarioInformacao.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                realizarAvaliacao();
            }
        });
    }

    private void realizarAvaliacao(){

        ResultadoDoencas fatores = ResultadoDoencas.getInstance();
        fatores.limpaResultado();

        System.out.println("Diabetes: " + fatores.getQtdDiabetes());
        System.out.println("Hipertensao: " + fatores.getQtdHipertensao());
        System.out.println("Doenças Cardiovasculares: " + fatores.getQtdDoencasCardiovasculares());
        System.out.println("Obesidade: " + fatores.getQtdObesidade());
        System.out.println("Sindrome Metabolica: " + fatores.getQtdSindromeMetabolica());


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

            fatores.setIMC(ParseUser.getCurrentUser().getDouble("altura"), informacoesMutaveis.getInt("peso"));
            fatores.setFumante(informacoesMutaveis.getBoolean("fumante"));
            fatores.setSedentarismo(informacoesMutaveis.getBoolean("sedentarismo"));
            fatores.setAltoConsumoAlcool(informacoesMutaveis.getBoolean("altoConsumoAlcool"));
            fatores.setAltoConsumoSodio(informacoesMutaveis.getBoolean("altoConsumoSodio"));
            fatores.setAltoConsumoGorduraAcucares(informacoesMutaveis.getBoolean("altoConsumoAcucar"));
            fatores.setNivelColesterol(informacoesMutaveis.getString("nivelColesterol"));
            fatores.setMulherMenopausa(informacoesMutaveis.getBoolean("menopausa"));
            fatores.setMulherAnticoncepcionais(informacoesMutaveis.getBoolean("tomaAnticoncepcional"));
            fatores.setEstresse(informacoesMutaveis.getBoolean("estressado"));
            fatores.setMulherDiabeteGestacional(informacoesMutaveis.getBoolean("diabetesGestacional"));
            fatores.setUsoCortisona(informacoesMutaveis.getBoolean("cortisona"));
            fatores.setUsoDiureticos(informacoesMutaveis.getBoolean("diuretico"));
            fatores.setUsoBetabloqueadores(informacoesMutaveis.getBoolean("betaBloqueador"));
            fatores.setMulherComFilhos(informacoesMutaveis.getBoolean("teveFilhos"));
            fatores.setHomemMoraComCompanheira(informacoesMutaveis.getBoolean("companheira"));
            fatores.setMulherOvarioPolicistico(informacoesMutaveis.getBoolean("ovarioPolicistico"));
            fatores.setDislipidemia(informacoesMutaveis.getBoolean("dislipidemia"));
            fatores.setMicroalbuminuria(informacoesMutaveis.getBoolean("microalbuminuria"));
            fatores.setIntoleranciaGlicose(informacoesMutaveis.getBoolean("intoleranciaGlicose"));
            fatores.setIntoleranciaInsulina(informacoesMutaveis.getBoolean("intoleranciaInsulina"));
            fatores.setHiperuricemia(informacoesMutaveis.getBoolean("hiperuricemia"));
            fatores.setEstadoProTromboticoProInflamatorio(informacoesMutaveis.getBoolean("proTrombotico"));


            //Informacoes do Usuário
            fatores.setSexo(ParseUser.getCurrentUser().getString("sexo"));

            String anoNascimento = ParseUser.getCurrentUser().get("dtNascimento").toString();
            String[] parts = anoNascimento.split("/");
            anoNascimento = parts[2];
            fatores.setIdade(Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(anoNascimento));
            fatores.setRaca(ParseUser.getCurrentUser().getString("raca"));


            //Informacoes Imutaveis
            ParseObject informacoesImutaveis = queryImutaveis.getFirst();
            fatores.setHipertensaoFamiliar(informacoesImutaveis.getBoolean("hipertensaoFamiliar"));
            fatores.setDiabetesFamiliar(informacoesImutaveis.getBoolean("diabetesFamiliar"));
            fatores.setCardiovascularFamiliar(informacoesImutaveis.getBoolean("cardiovascularFamiliar"));
            fatores.setObesidadeFamiliar(informacoesImutaveis.getBoolean("obesidadeFamiliar"));
            fatores.setSindromeFamiliar(informacoesImutaveis.getBoolean("sindromeFamiliar"));
            fatores.setDiabetes(informacoesImutaveis.getBoolean("diabetico"));
            fatores.setHipertensao(informacoesImutaveis.getBoolean("hipertenso"));

            stopLoading();

            Intent takeUserHomepage = new Intent(FormularioAvaliacao.this, ResultadoAvaliacao.class);
            startActivity(takeUserHomepage);


        }catch (ParseException e){
            System.out.println("e.message: " + e.getMessage());

        }
    }

}
