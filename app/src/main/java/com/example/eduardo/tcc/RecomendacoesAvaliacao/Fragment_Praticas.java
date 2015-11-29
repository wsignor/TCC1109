package com.example.eduardo.tcc.RecomendacoesAvaliacao;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.eduardo.tcc.Entidades.CurrentUser;
import com.example.eduardo.tcc.Nutricionista.Descricao_Pratica;
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
public class Fragment_Praticas extends android.support.v4.app.Fragment {

    View contentView2;
    private TextView textViewToChange;
    private String idDoencaVigente;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        contentView2 = inflater.inflate(R.layout.praticas_recomendados, null);

        idDoencaVigente = getActivity().getIntent().getExtras().getString("idDoenca");

        ParseObject doenca;

        carregaListaPraticas(idDoencaVigente, contentView2);

        String nomeDoenca = "";
        try {
            ParseQuery<ParseObject> innerQuery = ParseQuery.getQuery("Doenca");
            innerQuery.whereEqualTo("objectId", idDoencaVigente);

            doenca = innerQuery.getFirst();
            nomeDoenca = doenca.fetchIfNeeded().getString("nome");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        textViewToChange = (TextView) contentView2.findViewById(R.id.textHabitosRecomendados);
        textViewToChange.setText("As praticas recomendadas para a " + nomeDoenca + " são:");

        return contentView2;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void carregaListaPraticas (String idDoenca, View contentView2){
        List<ParseObject> listPraticas = new ArrayList<ParseObject>();
        ListView listview;

        ParseQuery<ParseObject> innerQuery = ParseQuery.getQuery("Doenca");
        innerQuery.whereEqualTo("objectId", idDoenca);


        ParseQuery<com.parse.ParseObject> queryPraticas = ParseQuery.getQuery("DoencaPratica");
        queryPraticas.whereMatchesQuery("idDoenca", innerQuery);

        ArrayList<String> listaExibida = new ArrayList<String>();

        try {
            listPraticas = queryPraticas.find();

            for(ParseObject parseObj : listPraticas){
                System.out.println("parseObj: " + parseObj.getObjectId());
                listaExibida.add(parseObj.get("descricao").toString());
            }

            if(!listaExibida.isEmpty()) {
                listview = (ListView) contentView2.findViewById(R.id.listViewHabitos);

                final StableArrayAdapter adapter = new StableArrayAdapter(contentView2.getContext(),
                android.R.layout.simple_list_item_1, listaExibida);
                listview.setAdapter(adapter);

                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent();
                        intent.setClass(view.getContext(), Descricao_Pratica.class);
                        intent.putExtra("position", position);
                        intent.putExtra("idDoenca", idDoencaVigente);
                        startActivity(intent);
                    }
                });

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


}
