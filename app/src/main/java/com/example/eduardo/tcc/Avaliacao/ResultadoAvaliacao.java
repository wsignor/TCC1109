package com.example.eduardo.tcc.Avaliacao;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eduardo.tcc.Entidades.Avaliacao;
import com.example.eduardo.tcc.Entidades.CurrentUser;
import com.example.eduardo.tcc.Entidades.Doenca;
import com.example.eduardo.tcc.Notification.ScheduleClient;
import com.example.eduardo.tcc.R;
import com.example.eduardo.tcc.RecomendacoesAvaliacao.PraticasNutricionais;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Eduardo on 05/10/2015.
 */
public class ResultadoAvaliacao extends Activity {

    // This is a handle so that we can call methods on our service
    private ScheduleClient scheduleClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultado_avaliacao);


        scheduleClient = new ScheduleClient(this);
        scheduleClient.doBindService();



        RelativeLayout rl = (RelativeLayout)findViewById(R.id.rlResultadoAvaliacao);

        int idBotao = 1;
        int y = 0;
        Doenca doencaAux;

        List<Doenca> doencas = CurrentUser.getDoencasTemp();


        for (int x = 0; x < doencas.size(); x++){
            y = x + 1;
            while(y < doencas.size()){
                if(doencas.get(x).getQtdOcorrencias() < doencas.get(y).getQtdOcorrencias()){
                    doencaAux = doencas.get(x);
                    doencas.set(x, doencas.get(y));
                    doencas.set(y, doencaAux);
                }
                y++;
            }
        }

        for (int x = 0; x < doencas.size(); x++){
            if(doencas.get(x).getQtdOcorrencias() > 0) {
                Button btn = new Button(this);
                btn.setText(doencas.get(x).getNome());
                btn.setTag(doencas.get(x).getIdDoenca());
                btn.setId(idBotao);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String objectIdAvaliacaoTemp = CurrentUser.getAvaliacaoTemp().getObjectId();
                        Avaliacao.getInstance().salvarAvaliacao(objectIdAvaliacaoTemp, v.getTag().toString(), v.getContext());
                        Avaliacao.getInstance().removeAvaliacaoTemp(v.getContext());

                        Intent intent = new Intent();
                        intent.setClass(v.getContext(), PraticasNutricionais.class);
                        intent.putExtra("idDoenca", CurrentUser.getAvaliacao().getParseObject("idDoenca").getObjectId());
                        intent.putExtra("idAvaliacao", CurrentUser.getAvaliacao().getObjectId());
                        startActivity(intent);

                        //enviarNotificacoes();

                        // colocar dados corretos
                        enviarEmail("nutri@nutri", v.getTag().toString(), "5");

                    }
                });

                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                if(idBotao == 1){
                    lp.addRule(RelativeLayout.BELOW, R.id.txtIntroducao);
                }else {
                    lp.addRule(RelativeLayout.BELOW, idBotao - 1);
                }
                lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 1);
                lp.addRule(RelativeLayout.ALIGN_PARENT_START, 1);
                lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1);
                lp.addRule(RelativeLayout.ALIGN_PARENT_END, 1);
                rl.addView(btn, lp);
                idBotao++;
            }
        }


        TextView txtConclusao = (TextView)findViewById(R.id.txtConclusao);

        RelativeLayout.LayoutParams lpConclusao = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lpConclusao.addRule(RelativeLayout.BELOW, idBotao - 1);

        txtConclusao.setLayoutParams(lpConclusao);
    }


    /**
     *  criar notificacoes pelos proximos 14 dias
     */
    private void enviarNotificacoes() {
        //scheduleClient = new ScheduleClient(this);

        int dia = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int mes = Calendar.getInstance().getTime().getMonth();
        int ano = Calendar.getInstance().get(Calendar.YEAR);

        System.out.print("dia: " + dia + " - mes: " + mes + " - ano: " + ano);

        for(int i = 0 ; i <= 14 ; i++){
            //System.out.println("MAXIMUM: " + Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));

            if(dia > Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)){
                dia = 1;
                mes = mes + 1;
            }

            if(mes > 12){
                mes = 1;
                ano = ano + 1;
            }

            onDateSelectedButtonClick(dia,mes,ano);

            dia++;
        }
    }


    /**
     * This is the onClick called from the method above
     */
    public void onDateSelectedButtonClick(int dia, int mes, int ano){
        // Get the date from our datepicker
        int day = 26;//picker.getDayOfMonth();
        int month = 11;//picker.getMonth();
        int year = 2015;//picker.getYear();

        // Create a new calendar set to the date chosen
        // we set the time to midnight (i.e. the first minute of that day)
        Calendar c = Calendar.getInstance();
        //c.set(c.getTime().getYear(), c.getTime().getMonth(), c.getTime().getDay());

        c.set(ano, mes, dia);

        //c.set(Calendar.HOUR_OF_DAY, c.getTime().getHours());
        //c.set(Calendar.MINUTE, c.getTime().getMinutes());
        //c.set(Calendar.SECOND, c.getTime().getSeconds()+5);

        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        System.out.println(c.toString());
        // Ask our service to set an alarm for that date, this activity talks to the client that talks to the service
        scheduleClient.setAlarmForNotification(c);
        // Notify the user what they just did
        //Toast.makeText(this, "Notification set for: " + day + "/" + (month + 1) + "/" + year, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Notification set for: " + dia + "/" + (mes + 1) + "/" + ano, Toast.LENGTH_SHORT).show();
    }


    private void enviarEmail(String emailNutri, String idDoenca, String fatores){

        String emailNutricionista = "";
        String nomeDoenca = "";

        StringBuilder builderBody = new StringBuilder();
        builderBody.append("Dados do(a) cliente " + ParseUser.getCurrentUser().get("nome").toString());
        builderBody.append(System.getProperty("line.separator"));


        try {
            ParseQuery<ParseObject> queryNomeDoenca = ParseQuery.getQuery("Doenca");
            queryNomeDoenca.whereEqualTo("objectId", idDoenca);
            nomeDoenca = queryNomeDoenca.getFirst().get("nome").toString();
            builderBody.append("Doença selecionada: " + nomeDoenca);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        try {
            ParseQuery<ParseObject> queryEmailNutri = ParseQuery.getQuery("_User");
            queryEmailNutri.whereEqualTo("objectId", ParseUser.getCurrentUser().getParseObject("idNutricionista").getObjectId());
            emailNutricionista = queryEmailNutri.getFirst().get("email").toString();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        builderBody.append("Informações imutáveis: ");


        try {
            ParseQuery<ParseObject> queryInformacoesImutaveis = ParseQuery.getQuery("InformacoesImutaveis");
            queryInformacoesImutaveis.whereEqualTo("idUsuario", ParseObject.createWithoutData("_User", ParseUser.getCurrentUser().getObjectId()));
            String hipertensaoFamiliar = queryInformacoesImutaveis.getFirst().get("hipertensaoFamiliar").toString();
            String diabetesFamiliar = queryInformacoesImutaveis.getFirst().get("diabetesFamiliar").toString();
            String cardiovascularFamiliar = queryInformacoesImutaveis.getFirst().get("cardiovascularFamiliar").toString();
            String obesidadeFamiliar = queryInformacoesImutaveis.getFirst().get("obesidadeFamiliar").toString();
            String sindromeFamiliar = queryInformacoesImutaveis.getFirst().get("sindromeFamiliar").toString();
            String diabetico = queryInformacoesImutaveis.getFirst().get("diabetico").toString();
            String hipertenso = queryInformacoesImutaveis.getFirst().get("hipertenso").toString();

            builderBody.append("Hipertensão Familiar: " + hipertensaoFamiliar);
            builderBody.append("Diabetes Familiar: " + diabetesFamiliar);
            builderBody.append("Cardiovascular Familiar: " + cardiovascularFamiliar);
            builderBody.append("Obesidade Familiar: " + obesidadeFamiliar);
            builderBody.append("Síndrome Familiar: " + sindromeFamiliar);
            builderBody.append("Diabético: " + diabetico);
            builderBody.append("Hipertenso: " + hipertenso);

        } catch (ParseException e) {
            e.printStackTrace();
        }



        builderBody.append(System.getProperty("line.separator"));
        builderBody.append("Informações mutáveis: ");

        try {
            ParseQuery<ParseObject> queryUsuarioInformacao = ParseQuery.getQuery("UsuarioInformacao");
            queryUsuarioInformacao.whereEqualTo("idUsuario", ParseObject.createWithoutData("_User", ParseUser.getCurrentUser().getObjectId()));
            String idInformacao = queryUsuarioInformacao.getFirst().get("idInformacao").toString();


            ParseQuery<ParseObject> queryInformacoesMutaveis = ParseQuery.getQuery("InformacoesMutaveis");
            queryInformacoesMutaveis.whereEqualTo("objectId", idInformacao);
            // parou aqui
            String fumante = queryInformacoesMutaveis.getFirst().get("fumante").toString();
            String tomaAnticoncepcional = queryInformacoesMutaveis.getFirst().get("tomaAnticoncepcional").toString();
            String peso = queryInformacoesMutaveis.getFirst().get("peso").toString();
            String nivelColesterol = queryInformacoesMutaveis.getFirst().get("nivelColesterol").toString();
            String nivelTriglicerideos = queryInformacoesMutaveis.getFirst().get("nivelTriglicerideos").toString();
            String altoConsumoAlcool = queryInformacoesMutaveis.getFirst().get("altoConsumoAlcool").toString();
            String altoConsumoSodio = queryInformacoesMutaveis.getFirst().get("altoConsumoSodio").toString();
            String altoConsumoAcucar = queryInformacoesMutaveis.getFirst().get("altoConsumoAcucar").toString();
            String menopausa = queryInformacoesMutaveis.getFirst().get("menopausa").toString();
            String estressado = queryInformacoesMutaveis.getFirst().get("estressado").toString();
            String diabetesGestacional = queryInformacoesMutaveis.getFirst().get("diabetesGestacional").toString();
            String cortisona = queryInformacoesMutaveis.getFirst().get("cortisona").toString();
            String diuretico = queryInformacoesMutaveis.getFirst().get("diuretico").toString();
            String betaBloqueador = queryInformacoesMutaveis.getFirst().get("betaBloqueador").toString();
            String teveFilhos = queryInformacoesMutaveis.getFirst().get("teveFilhos").toString();
            String companheira = queryInformacoesMutaveis.getFirst().get("companheira").toString();
            String ovarioPolicistico = queryInformacoesMutaveis.getFirst().get("ovarioPolicistico").toString();
            String dislipidemia = queryInformacoesMutaveis.getFirst().get("dislipidemia").toString();
            String microalbuminuria = queryInformacoesMutaveis.getFirst().get("microalbuminuria").toString();
            String intoleranciaGlicose = queryInformacoesMutaveis.getFirst().get("intoleranciaGlicose").toString();
            String intoleranciaInsulina = queryInformacoesMutaveis.getFirst().get("intoleranciaInsulina").toString();
            String hiperuricemia = queryInformacoesMutaveis.getFirst().get("hiperuricemia").toString();
            String proTrombotico = queryInformacoesMutaveis.getFirst().get("proTrombotico").toString();
            String sedentarismo = queryInformacoesMutaveis.getFirst().get("sedentarismo").toString();


            builderBody.append("É Fumante: " + fumante);
            builderBody.append("Toma Anticoncepcional: " + tomaAnticoncepcional);
            builderBody.append("Peso: " + peso);
            builderBody.append("Nível Colesterol: " + nivelColesterol);
            builderBody.append("Nível Triglicerídeos: " + nivelTriglicerideos);
            builderBody.append("Alto consumo de álcool: " + altoConsumoAlcool);
            builderBody.append("Alto consumo de sódio: " + altoConsumoSodio);
            builderBody.append("Alto consumo de açucar: " + altoConsumoAcucar);
            builderBody.append("Menopausa: " + menopausa);
            builderBody.append("Estressado: " + estressado);
            builderBody.append("Diabetes Gestacional: " + diabetesGestacional);
            builderBody.append("Cortisona: " + cortisona);
            builderBody.append("Diurético: " + diuretico);
            builderBody.append("Beta bloqueador: " + betaBloqueador);
            builderBody.append("Teve filhos: " + teveFilhos);
            builderBody.append("Companheira: " + companheira);
            builderBody.append("Ovário Policístico: " + ovarioPolicistico);
            builderBody.append("Dislipidemia: " + dislipidemia);
            builderBody.append("Microalbuminuria: " + microalbuminuria);
            builderBody.append("Intolerancia à Glicose: " + intoleranciaGlicose);
            builderBody.append("Intolerancia à Insulina: " + intoleranciaInsulina);
            builderBody.append("Hiperuricemia: " + hiperuricemia);
            builderBody.append("Pró Trombotico: " + proTrombotico);
            builderBody.append("Sedentarismo: " + sedentarismo);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setType("plain/text");
        sendIntent.setData(Uri.parse(emailNutricionista));
        sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
        sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { emailNutricionista });
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Um cliente seu concluiu a avaliação, veja o resultado.");
        sendIntent.putExtra(Intent.EXTRA_TEXT, builderBody.toString());
        startActivity(sendIntent);
    }
}
