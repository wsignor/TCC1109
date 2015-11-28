package com.example.eduardo.tcc.Avaliacao;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eduardo.tcc.Entidades.Avaliacao;
import com.example.eduardo.tcc.Entidades.CurrentUser;
import com.example.eduardo.tcc.Entidades.Doenca;
import com.example.eduardo.tcc.Notification.ScheduleClient;
import com.example.eduardo.tcc.R;
import com.example.eduardo.tcc.RecomendacoesAvaliacao.PraticasNutricionais;

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

                        Intent takeUserHomepage = new Intent(v.getContext(), PraticasNutricionais.class);
                        startActivity(takeUserHomepage);

                        //enviarNotificacoes();

                        // colocar dados corretos
                        //enviarEmail("nutri@nutri", "hipertensao", "5");

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


    private void enviarEmail(String emailNutri, String doenca, String fatores){
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setType("plain/text");
        sendIntent.setData(Uri.parse("test@gmail.com"));
        sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
        sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "test@gmail.com" });
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Um cliente seu concluiu a avaliação, veja o resultado.");
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Corpo do email com as informações necessárias.");
        startActivity(sendIntent);
    }
}
