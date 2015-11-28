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
                        double percentualFatores = (( doencaAvaliacaoTemp.fetchIfNeeded().getInt("qtdFatores")) * 100 ) / 20;
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

/*                LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                        new DataPoint(0, percentuaisFatores.size() > 0 ? percentuaisFatores.get(0).doubleValue() : 0.0),
                        new DataPoint(1, percentuaisFatores.size() > 1 ? percentuaisFatores.get(1).doubleValue() : 0.0),
                        new DataPoint(2, percentuaisFatores.size() > 2 ? percentuaisFatores.get(2).doubleValue() : 0.0),
                        new DataPoint(3, percentuaisFatores.size() > 3 ? percentuaisFatores.get(3).doubleValue() : 0.0),
                        new DataPoint(4, percentuaisFatores.size() > 4 ? percentuaisFatores.get(4).doubleValue() : 0.0),
                        new DataPoint(5, percentuaisFatores.size() > 5 ? percentuaisFatores.get(5).doubleValue() : 0.0),
                });*/



                series.setTitle(doenca.getString("nome"));
                Random rnd = new Random();
                int color = Color.argb(255, rnd.nextInt(256),rnd.nextInt(256),rnd.nextInt(256));
                series.setColor(color);

                graph.addSeries(series);
            }


            graph.getGridLabelRenderer().setVerticalAxisTitle("Percent. fatores");//setNumHorizontalLabels(3);
            graph.getGridLabelRenderer().setHorizontalAxisTitle("Avaliações");
            graph.getGridLabelRenderer().setPadding(70);



            // set manual Y bounds
            graph.getViewport().setYAxisBoundsManual(true);
            graph.getViewport().setMinY(0);
            graph.getViewport().setMaxY(100);

            // set manual X bounds
            graph.getViewport().setXAxisBoundsManual(true);
            graph.getViewport().setMinX(1);
            graph.getViewport().setMaxX(avaliacoes.size());

            graph.getLegendRenderer().setVisible(true);
            graph.getLegendRenderer().setFixedPosition(0, 200);


            /*LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                    new DataPoint(0, 80),
                    new DataPoint(1, 80),
                    new DataPoint(2, 50),
                    new DataPoint(3, 50),
                    new DataPoint(4, 20),
                    new DataPoint(5, 30)
            });
            series.setTitle("Hipertensão");

            LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(new DataPoint[]{
                    new DataPoint(0, 80),
                    new DataPoint(1, 50),
                    new DataPoint(2, 50),
                    new DataPoint(3, 30),
                    new DataPoint(4, 20),
                    new DataPoint(5, 10)
            });

            series2.setTitle("Diabetes");
            series2.setColor(Color.RED);

            LineGraphSeries<DataPoint> series3 = new LineGraphSeries<DataPoint>(new DataPoint[]{
                    new DataPoint(0, 70),
                    new DataPoint(1, 90),
                    new DataPoint(2, 20),
                    new DataPoint(3, 30),
                    new DataPoint(4, 80),
                    new DataPoint(5, 70)
            });

            series3.setTitle("Cardiovasculares");
            series3.setColor(Color.GREEN);

            LineGraphSeries<DataPoint> series4 = new LineGraphSeries<DataPoint>(new DataPoint[]{
                    new DataPoint(0, 30),
                    new DataPoint(1, 40),
                    new DataPoint(2, 50),
                    new DataPoint(3, 80),
                    new DataPoint(4, 30),
                    new DataPoint(5, 20)
            });

            series4.setTitle("Obesidade");
            series4.setColor(Color.MAGENTA);

            LineGraphSeries<DataPoint> series5 = new LineGraphSeries<DataPoint>(new DataPoint[]{
                    new DataPoint(0, 50),
                    new DataPoint(1, 40),
                    new DataPoint(2, 60),
                    new DataPoint(3, 60),
                    new DataPoint(4, 40),
                    new DataPoint(5, 40)
            });

            series5.setTitle("Síndrome metab.");
            series5.setColor(Color.CYAN);

            //graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(Grafico.this));

            graph.getGridLabelRenderer().setVerticalAxisTitle("Percent. fatores");//setNumHorizontalLabels(3);

            graph.getGridLabelRenderer().setHorizontalAxisTitle("Avaliações");
            graph.getGridLabelRenderer().setPadding(70);

//        graph.getViewport().setScalable(true);
//        graph.getViewport().setScrollable(true);

            // set manual Y bounds
            graph.getViewport().setYAxisBoundsManual(true);
            graph.getViewport().setMinY(0);
            graph.getViewport().setMaxY(100);

            // set manual X bounds
            graph.getViewport().setXAxisBoundsManual(true);
            graph.getViewport().setMinX(1);
            graph.getViewport().setMaxX(5);

            graph.addSeries(series);
            graph.addSeries(series2);
            graph.addSeries(series3);
            graph.addSeries(series4);
            graph.addSeries(series5);
            graph.getLegendRenderer().setVisible(true);
            graph.getLegendRenderer().setFixedPosition(0, 200);*/
        }
    }
}
