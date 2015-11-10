package com.example.eduardo.tcc.RecomendacoesAvaliacao;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.eduardo.tcc.R;
import com.example.eduardo.tcc.Util.Mask;
import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Eduardo on 14/08/2015.
 */
public class Fragment_Nutricionais extends android.support.v4.app.Fragment {

    View contentView;
    private TextView textViewToChange;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        contentView = inflater.inflate(R.layout.orientacoes_nutricionais, null);

        textViewToChange = (TextView) contentView.findViewById(R.id.textOrientacaoNutricional);
        textViewToChange.setText("As orientações nutricionais indicados pelo seu nutricionista são:");

        return contentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
