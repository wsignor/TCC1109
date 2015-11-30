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

                        enviarNotificacoes();

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

    @Override
    protected void onStop() {
        // When our activity is stopped ensure we also stop the connection to the service
        // this stops us leaking our activity into the system *bad*
        if(scheduleClient != null)
            scheduleClient.doUnbindService();
        super.onStop();
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

            onDateSelectedButtonClick(5,11,ano);

            dia++;
        }
    }


    /**
     * This is the onClick called from the method above
     */
    public void onDateSelectedButtonClick(int dia, int mes, int ano){
        // Get the date from our datepicker
//        int day = 26;//picker.getDayOfMonth();
//        int month = 11;//picker.getMonth();
//        int year = 2015;//picker.getYear();

        // Create a new calendar set to the date chosen
        // we set the time to midnight (i.e. the first minute of that day)
        Calendar c = Calendar.getInstance();
        //c.set(c.getTime().getYear(), c.getTime().getMonth(), c.getTime().getDay());

        c.set(ano, mes, dia);

        //c.set(Calendar.HOUR_OF_DAY, c.getTime().getHours());
        //c.set(Calendar.MINUTE, c.getTime().getMinutes());
        //c.set(Calendar.SECOND, c.getTime().getSeconds()+5);

//        c.set(Calendar.HOUR_OF_DAY, 0);
//        c.set(Calendar.MINUTE, 0);
//        c.set(Calendar.SECOND, 0);

        System.out.println(c.toString());
        // Ask our service to set an alarm for that date, this activity talks to the client that talks to the service
        scheduleClient.setAlarmForNotification(c);
        // Notify the user what they just did
        //Toast.makeText(this, "Notification set for: " + day + "/" + (month + 1) + "/" + year, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Notification set for: " + dia + "/" + (mes + 1) + "/" + ano, Toast.LENGTH_SHORT).show();
    }


    private void enviarEmail(String emailNutri, String idDoenca, String fatores){

        String emailNutricionista = "" ;
        String nomeDoenca = "";

        StringBuilder builderBody = new StringBuilder();
        builderBody.append("Dados do(a) cliente " + ParseUser.getCurrentUser().get("nome").toString());
        builderBody.append(System.getProperty("line.separator"));


        try {
            ParseQuery<ParseObject> queryNomeDoenca = ParseQuery.getQuery("Doenca");
            queryNomeDoenca.whereEqualTo("objectId", idDoenca);
            nomeDoenca = queryNomeDoenca.getFirst().get("nome").toString();
            builderBody.append("\nDoença selecionada: \n" + nomeDoenca + "\n");
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

        builderBody.append("\nInformações Imutáveis: ");


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

            builderBody.append("\nHipertensão Familiar: " + (hipertensaoFamiliar.equalsIgnoreCase("true") ? "Sim" : "Não"));
            builderBody.append("\nDiabetes Familiar: " + (diabetesFamiliar.equalsIgnoreCase("true") ? "Sim" : "Não"));
            builderBody.append("\nCardiovascular Familiar: " + (cardiovascularFamiliar.equalsIgnoreCase("true") ? "Sim" : "Não"));
            builderBody.append("\nObesidade Familiar: " + (obesidadeFamiliar.equalsIgnoreCase("true") ? "Sim" : "Não"));
            builderBody.append("\nSíndrome Familiar: " + (sindromeFamiliar.equalsIgnoreCase("true") ? "Sim" : "Não"));
            builderBody.append("\nDiabético: " + (diabetico.equalsIgnoreCase("true") ? "Sim" : "Não"));
            builderBody.append("\nHipertenso: " + (hipertenso.equalsIgnoreCase("true") ? "Sim" : "Não"));

        } catch (ParseException e) {
            e.printStackTrace();
        }



        builderBody.append(System.getProperty("line.separator"));
        builderBody.append("\n\nInformações mutáveis: ");

        try {

            ParseQuery innerQuery = new ParseQuery("_User");
            innerQuery.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());

            ParseQuery<ParseObject> queryImutaveis = ParseQuery.getQuery("InformacoesImutaveis");
            queryImutaveis.whereMatchesQuery("idUsuario", innerQuery);

            ParseQuery<ParseObject> queryUsuarioInformacao = ParseQuery.getQuery("UsuarioInformacao");
            queryUsuarioInformacao.whereMatchesQuery("idUsuario", innerQuery);
            queryUsuarioInformacao.addDescendingOrder("versao");

            //Informacoes Mutaveissss
            ParseObject informacoesMutaveis = queryUsuarioInformacao.getFirst();
            informacoesMutaveis = informacoesMutaveis.getParseObject("idInformacao").fetch();


            String fumante = informacoesMutaveis.get("fumante").toString();
            String tomaAnticoncepcional = informacoesMutaveis.get("tomaAnticoncepcional").toString();
            String peso = informacoesMutaveis.get("peso").toString();
            String nivelColesterol = informacoesMutaveis.get("nivelColesterol").toString();
            String nivelTriglicerideos = informacoesMutaveis.get("nivelTriglicerideos").toString();
            String altoConsumoAlcool = informacoesMutaveis.get("altoConsumoAlcool").toString();
            String altoConsumoSodio = informacoesMutaveis.get("altoConsumoSodio").toString();
            String altoConsumoAcucar = informacoesMutaveis.get("altoConsumoAcucar").toString();
            String menopausa = informacoesMutaveis.get("menopausa").toString();
            String estressado = informacoesMutaveis.get("estressado").toString();
            String diabetesGestacional = informacoesMutaveis.get("diabetesGestacional").toString();
            String cortisona = informacoesMutaveis.get("cortisona").toString();
            String diuretico = informacoesMutaveis.get("diuretico").toString();
            String betaBloqueador = informacoesMutaveis.get("betaBloqueador").toString();
            String teveFilhos = informacoesMutaveis.get("teveFilhos").toString();
            String companheira = informacoesMutaveis.get("companheira").toString();
            String ovarioPolicistico = informacoesMutaveis.get("ovarioPolicistico").toString();
            String dislipidemia = informacoesMutaveis.get("dislipidemia").toString();
            String microalbuminuria = informacoesMutaveis.get("microalbuminuria").toString();
            String intoleranciaGlicose = informacoesMutaveis.get("intoleranciaGlicose").toString();
            String intoleranciaInsulina = informacoesMutaveis.get("intoleranciaInsulina").toString();
            String hiperuricemia = informacoesMutaveis.get("hiperuricemia").toString();
            String proTrombotico = informacoesMutaveis.get("proTrombotico").toString();
            String sedentarismo = informacoesMutaveis.get("sedentarismo").toString();


            builderBody.append("\nÉ Fumante: " + (fumante.equalsIgnoreCase("true") ? "Sim" : "Não"));
            builderBody.append("\nToma Anticoncepcional: " + (tomaAnticoncepcional.equalsIgnoreCase("true") ? "Sim" : "Não"));
            builderBody.append("\nPeso: " + peso + "Kg");
            builderBody.append("\nNível Colesterol: " + (nivelColesterol.equalsIgnoreCase("true") ? "Sim" : "Não"));
            builderBody.append("\nNível Triglicerídeos: " + (nivelTriglicerideos.equalsIgnoreCase("true") ? "Sim" : "Não"));
            builderBody.append("\nAlto consumo de álcool: " + (altoConsumoAlcool.equalsIgnoreCase("true") ? "Sim" : "Não"));
            builderBody.append("\nAlto consumo de sódio: " + (altoConsumoSodio.equalsIgnoreCase("true") ? "Sim" : "Não"));
            builderBody.append("\nAlto consumo de açucar: " + (altoConsumoAcucar.equalsIgnoreCase("true") ? "Sim" : "Não"));
            builderBody.append("\nMenopausa: " + (menopausa.equalsIgnoreCase("true") ? "Sim" : "Não"));
            builderBody.append("\nEstressado: " + (estressado.equalsIgnoreCase("true") ? "Sim" : "Não"));
            builderBody.append("\nDiabetes Gestacional: " + (diabetesGestacional.equalsIgnoreCase("true") ? "Sim" : "Não"));
            builderBody.append("\nCortisona: " + (cortisona.equalsIgnoreCase("true") ? "Sim" : "Não"));
            builderBody.append("\nDiurético: " + (diuretico.equalsIgnoreCase("true") ? "Sim" : "Não"));
            builderBody.append("\nBeta bloqueador: " + (betaBloqueador.equalsIgnoreCase("true") ? "Sim" : "Não"));
            builderBody.append("\nTeve filhos: " + (teveFilhos.equalsIgnoreCase("true") ? "Sim" : "Não"));
            builderBody.append("\nCompanheira: " + (companheira.equalsIgnoreCase("true") ? "Sim" : "Não"));
            builderBody.append("\nOvário Policístico: " + (ovarioPolicistico.equalsIgnoreCase("true") ? "Sim" : "Não"));
            builderBody.append("\nDislipidemia: " + (dislipidemia.equalsIgnoreCase("true") ? "Sim" : "Não"));
            builderBody.append("\nMicroalbuminuria: " + (microalbuminuria.equalsIgnoreCase("true") ? "Sim" : "Não"));
            builderBody.append("\nIntolerancia à Glicose: " + (intoleranciaGlicose.equalsIgnoreCase("true") ? "Sim" : "Não"));
            builderBody.append("\nIntolerancia à Insulina: " + (intoleranciaInsulina.equalsIgnoreCase("true") ? "Sim" : "Não"));
            builderBody.append("\nHiperuricemia: " + (hiperuricemia.equalsIgnoreCase("true") ? "Sim" : "Não"));
            builderBody.append("\nPró Trombotico: " + (proTrombotico.equalsIgnoreCase("true") ? "Sim" : "Não"));
            builderBody.append("\nSedentarismo: " + (sedentarismo.equalsIgnoreCase("true") ? "Sim" : "Não"));


        } catch (ParseException e) {
            e.printStackTrace();
        }

        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setType("plain/text");
        sendIntent.setData(Uri.parse(emailNutricionista));
        sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
        sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { emailNutricionista });
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Nova avaliação do(a) " + ParseUser.getCurrentUser().get("nome").toString());
        sendIntent.putExtra(Intent.EXTRA_TEXT, builderBody.toString());
        startActivity(sendIntent);
    }
}
