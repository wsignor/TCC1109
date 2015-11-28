package com.example.eduardo.tcc.Entidades;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eduardo on 06/10/2015.
 */
public final class CurrentUser {

    // Variáveis
    private static CurrentUser INSTANCE = new CurrentUser();
    private static List<Doenca> doencasTemp;
    private static ParseObject avaliacaoTemp;
    private static ParseObject avaliacao;

    public CurrentUser() {
        if (ParseUser.getCurrentUser() != null) {
            carregaAvaliacao();
        }
    }

    // gets e sets
    public static List<Doenca> getDoencasTemp() {
        return doencasTemp;
    }
    public static ParseObject getAvaliacaoTemp() {
        return avaliacaoTemp;
    }
    public static void setAvaliacaoTemp(ParseObject avaliacaoTemp) { avaliacaoTemp = avaliacaoTemp; }
    public static ParseObject getAvaliacao() {
        return avaliacao;
    }
    public static void setAvaliacao(ParseObject avaliacao) { avaliacao = avaliacao; }
    public static CurrentUser getInstance(){
        return INSTANCE;
    }


    // Funções
    public static void startInstance(){
        INSTANCE = new CurrentUser();
    }
    public static void carregaAvaliacao(){
        ParseQuery innerQuery = new ParseQuery("_User");
        innerQuery.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());

        ParseQuery<com.parse.ParseObject> queryAvaliacaoTemp = ParseQuery.getQuery("AvaliacaoTemp");
        queryAvaliacaoTemp.whereMatchesQuery("idUsuario", innerQuery);

        queryAvaliacaoTemp.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                avaliacaoTemp = parseObject;
            }
        });

        ParseQuery<com.parse.ParseObject> queryDoencasAvaliacao = ParseQuery.getQuery("DoencaAvaliacaoTemp");
        queryDoencasAvaliacao.whereMatchesQuery("idAvaliacaoTemp", queryAvaliacaoTemp);

        try{
            List<ParseObject> doencasAvaliacao = queryDoencasAvaliacao.find();

            doencasTemp = new ArrayList<Doenca>();
            for (ParseObject obj : doencasAvaliacao) {
                try{
                    Doenca doencaAux = new Doenca();
                    ParseObject doenca = obj.getParseObject("idDoenca");
                    doencaAux.setNome(doenca.fetchIfNeeded().getString("nome"));
                    doencaAux.setQtdOcorrencias(obj.getInt("qtdFatores"));
                    doencaAux.setIdDoenca(doenca.getObjectId());
                    doencasTemp.add(doencaAux);
                }catch (ParseException exp){

                }
            }

        }catch (ParseException exp){

        }

        ParseQuery<com.parse.ParseObject> queryAvaliacao = ParseQuery.getQuery("Avaliacao");
        queryAvaliacao.whereMatchesQuery("idUsuario", innerQuery);
        queryAvaliacao.whereDoesNotExist("dtTermino");

        try{
            avaliacao = queryAvaliacao.getFirst();
        }catch (ParseException exp){

        }

    }
}
