package com.example.eduardo.tcc.RecomendacoesAvaliacao;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.example.eduardo.tcc.R;
import com.example.eduardo.tcc.Util.StableArrayAdapter;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eduardo on 14/08/2015.
 */
public class Fragment_Nutricionais extends android.support.v4.app.Fragment {

    View contentView;
    private TextView textViewToChange;
    private String idAvaliacao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.orientacoes_nutricionais, null);

        idAvaliacao = getActivity().getIntent().getExtras().getString("idAvaliacao");

        textViewToChange = (TextView) contentView.findViewById(R.id.textOrientacaoNutricional);
        textViewToChange.setText("As orientações nutricionais indicados pelo seu nutricionista são:");

        carregaListaAlimentos(contentView);

        return contentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void carregaListaAlimentos(View contentView){
        List<ParseObject> listaAlimentos = new ArrayList<ParseObject>();
        ListView listview;

        ParseQuery<ParseObject> innerQuery = ParseQuery.getQuery("Avaliacao");
        innerQuery.whereEqualTo("objectId", idAvaliacao);

        ParseQuery<com.parse.ParseObject> queryAlimentos = ParseQuery.getQuery("AlimentoAvaliacao");
        queryAlimentos.whereMatchesQuery("idAvaliacao", innerQuery);

        ArrayList<String> listaExibida = new ArrayList<String>();


        try {
            listaAlimentos = queryAlimentos.find();

            for(ParseObject parseObj : listaAlimentos){
                StringBuilder descricaoAlimento = new StringBuilder();
                descricaoAlimento.append(parseObj.get("descricaoAlimento").toString());
                if(parseObj.get("quantidade") != null){
                    descricaoAlimento.append(" - ");
                    descricaoAlimento.append(parseObj.get("quantidade").toString());
                }
                if(parseObj.get("periodicidade") != null){
                    descricaoAlimento.append(" - ");
                    descricaoAlimento.append(parseObj.get("periodicidade").toString());
                }
                listaExibida.add(descricaoAlimento.toString());
            }

            if(!listaExibida.isEmpty()) {
                listview = (ListView) contentView.findViewById(R.id.listViewAlimentos);

                final StableArrayAdapter adapter = new StableArrayAdapter(contentView.getContext(),
                        android.R.layout.simple_list_item_1, listaExibida);
                listview.setAdapter(adapter);

            }else{
                textViewToChange = (TextView) contentView.findViewById(R.id.textOrientacaoNutricional);
                textViewToChange.setText("Não há alimentos para serem apresentados.");
            }
        } catch (ParseException e) {

            e.printStackTrace();
        }

    }
}
