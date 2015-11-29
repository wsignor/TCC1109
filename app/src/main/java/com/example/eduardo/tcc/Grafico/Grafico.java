package com.example.eduardo.tcc.Grafico;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.eduardo.tcc.Avaliacao.FormularioAvaliacao;
import com.example.eduardo.tcc.Avaliacao.ResultadoAvaliacao;
import com.example.eduardo.tcc.CadastroUsuario.DadosUsuario;
import com.example.eduardo.tcc.Entidades.CurrentUser;
import com.example.eduardo.tcc.Entidades.Doenca;
import com.example.eduardo.tcc.Inicio.Login;
import com.example.eduardo.tcc.Nutricionista.ClientesNutricionista;
import com.example.eduardo.tcc.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Vector;


public class Grafico extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grafico);

        List<ParseObject> avaliacoes = new ArrayList<ParseObject>();
        List<ParseObject> doencas = new ArrayList<ParseObject>();
        ParseObject doencaAvaliacaoTemp;
        GraphView graph = (GraphView) findViewById(R.id.graph);

        ParseQuery queryUsuario = new ParseQuery("_User");
        ParseQuery<com.parse.ParseObject> queryAvaliacao = ParseQuery.getQuery("Avaliacao");
        ParseQuery queryDoencas = new ParseQuery("Doenca");
        ParseQuery queryDoencasAvaliacaoTemp = new ParseQuery("DoencaAvaliacaoTemp");
        List<Integer> cores = new ArrayList<Integer>();
        int corVigente=0;
        cores.add(Color.MAGENTA);
        cores.add(Color.RED);
        cores.add(Color.BLUE);
        cores.add(Color.GREEN);
        cores.add(Color.CYAN);
        cores.add(Color.BLACK);

        try {

            queryUsuario.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());

            queryAvaliacao.whereMatchesQuery("idUsuario", queryUsuario);
            queryAvaliacao.whereContains("finalizadaCorretamente", "S");

            avaliacoes = queryAvaliacao.find();

            doencas = queryDoencas.find();

        }catch (ParseException e){

        }

        if(avaliacoes.size()>0) {
            for (ParseObject doenca : doencas) {
                List<Double> percentuaisFatores = new ArrayList<Double>();
                for (int contatorAvaliacao=0;contatorAvaliacao<avaliacoes.size();contatorAvaliacao++) {

                    queryAvaliacao = ParseQuery.getQuery("Avaliacao");
                    queryAvaliacao.whereEqualTo("objectId", avaliacoes.get(contatorAvaliacao).getObjectId());

                    queryDoencas = ParseQuery.getQuery("Doenca");
                    queryDoencas.whereEqualTo("objectId", doenca.getObjectId());

                    queryDoencasAvaliacaoTemp = new ParseQuery("DoencaAvaliacaoTemp");
                    queryDoencasAvaliacaoTemp.whereMatchesQuery("idAvaliacao", queryAvaliacao);
                    queryDoencasAvaliacaoTemp.whereMatchesQuery("idDoenca", queryDoencas);

                    try{
                        doencaAvaliacaoTemp = queryDoencasAvaliacaoTemp.getFirst();
                        double percentualFatores = (( doencaAvaliacaoTemp.fetchIfNeeded().getInt("qtdFatores")) * 100 ) / doenca.getInt("qtdTotalFatores");
                        percentuaisFatores.add(percentualFatores);
                        //lines.appendData(new DataPoint(contatorAvaliacao, percentualFatores), true, avaliacoes.size());
                    }catch (ParseException e){
                        percentuaisFatores.add(0.0);
                    }
                }


                Vector<DataPoint> v = new Vector<DataPoint>();
                for (int i=1; i<=percentuaisFatores.size(); i++) {
                    v.add(new DataPoint(i, percentuaisFatores.get(i-1)));
                }

                DataPoint s[] = v.toArray(new DataPoint[percentuaisFatores.size()]);
                LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(s);

                series.setTitle(doenca.getString("nome"));
                series.setColor(cores.get(corVigente));

                graph.addSeries(series);
                corVigente++;
            }


            graph.getGridLabelRenderer().setVerticalAxisTitle("Fatores de Risco (%)");
            graph.getGridLabelRenderer().setHorizontalAxisTitle("Avaliações");
            graph.getGridLabelRenderer().setPadding(70);

            // set manual Y bounds
            graph.getViewport().setYAxisBoundsManual(true);
            graph.getViewport().setMinY(0);
            graph.getViewport().setMaxY(100);

            // set manual X bounds
            graph.getViewport().setXAxisBoundsManual(true);
            graph.getViewport().setMinX(1);
            graph.getViewport().setMinX(avaliacoes.size());


            graph.getLegendRenderer().setVisible(true);
            graph.getLegendRenderer().setFixedPosition(0, 200);
        }
    }
}
