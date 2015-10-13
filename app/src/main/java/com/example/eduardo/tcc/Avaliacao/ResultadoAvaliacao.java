package com.example.eduardo.tcc.Avaliacao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.eduardo.tcc.Entidades.Avaliacao;
import com.example.eduardo.tcc.Entidades.CurrentUser;
import com.example.eduardo.tcc.Entidades.Doenca;
import com.example.eduardo.tcc.Nutricionista.ListItemDetail;
import com.example.eduardo.tcc.R;
import com.example.eduardo.tcc.RecomendacoesAvaliacao.AlimentosPraticas;

import java.util.List;

/**
 * Created by Eduardo on 05/10/2015.
 */
public class ResultadoAvaliacao extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultado_avaliacao);

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
                btn.setText(doencas.get(x).getNome() + " (" + doencas.get(x).getQtdOcorrencias() + ")");
                btn.setTag(doencas.get(x).getIdDoenca());
                btn.setId(idBotao);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Avaliacao.getInstance().removeAvaliacaoTemp(v.getContext());

                        Intent intent = new Intent();
                        intent.setClass(v.getContext(), AlimentosPraticas.class);
                        intent.putExtra("idDoenca", v.getTag().toString());
                        startActivity(intent);

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
}
