package com.example.eduardo.tcc.RecomendacoesAvaliacao;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.example.eduardo.tcc.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by Eduardo on 14/08/2015.
 */
public class Fragment_Praticas extends android.support.v4.app.Fragment {

    View contentView2;
    private TextView textViewToChange;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        contentView2 = inflater.inflate(R.layout.praticas_recomendados, null);

        textViewToChange = (TextView) contentView2.findViewById(R.id.textHabitosRecomendados);
        textViewToChange.setText("As praticas recomendadas para a Hipertensão são:");

        return contentView2;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
