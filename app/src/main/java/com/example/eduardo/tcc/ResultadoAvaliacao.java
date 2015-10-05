package com.example.eduardo.tcc;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Eduardo on 05/10/2015.
 */
public class ResultadoAvaliacao extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultado_avaliacao);

        RelativeLayout rl = (RelativeLayout)findViewById(R.id.rlResultadoAvaliacao);

        ResultadoDoencas fatores = ResultadoDoencas.getInstance();

        int idBotao = 1;
        int y = 0;
        Doenca aux;

        Doenca[] qtdDoencas = new Doenca[] { fatores.getObesidade() , fatores.getDoencasCardiovasculares(), fatores.getHipertensao(), fatores.getDiabetes(), fatores.getSindromeMetabolica()};

        for (int x = 0; x < 5; x++){
            y = x + 1;
            while(y < 5){
                if(qtdDoencas[x].getQtdOcorrencias() < qtdDoencas[y].getQtdOcorrencias()){
                    aux = qtdDoencas[x];
                    qtdDoencas[x] = qtdDoencas[y];
                    qtdDoencas[y] = aux;
                }
                y++;
            }
        }

        for (int x = 0; x < 5; x++){
            if(qtdDoencas[x].getQtdOcorrencias() > 0) {
                Button btn = new Button(this);
                btn.setText(qtdDoencas[x].getNome() + " (" + qtdDoencas[x].getQtdOcorrencias() + ")");
                btn.setId(idBotao);

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


        System.out.println("Diabetes: " + fatores.getQtdDiabetes());
        System.out.println("Hipertensao: " + fatores.getQtdHipertensao());
        System.out.println("Doenças Cardiovasculares: " + fatores.getQtdDoencasCardiovasculares());
        System.out.println("Obesidade: " + fatores.getQtdObesidade());
        System.out.println("Sindrome Metabolica: " + fatores.getQtdSindromeMetabolica());

    }
}
