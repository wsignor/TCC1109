package com.example.eduardo.tcc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.parse.ParseUser;

/**
 * Created by Eduardo on 14/08/2015.
 */
public class Fragment_DadosPessoais extends android.support.v4.app.Fragment {

    View contentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        contentView = inflater.inflate(R.layout.dados_pessoais, null);

        Spinner spinner;

        spinner = (Spinner) contentView.findViewById( R.id.spnSexo );

        ArrayAdapter <CharSequence>adapterSexo = ArrayAdapter.createFromResource( getActivity(), R.array.sexo_array , android.R.layout.simple_spinner_item);
        adapterSexo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterSexo);

        spinner = (Spinner) contentView.findViewById( R.id.spnRaca );

        ArrayAdapter <CharSequence>adapterRaca = ArrayAdapter.createFromResource( getActivity(), R.array.raca_array , android.R.layout.simple_spinner_item);
        adapterRaca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterRaca);

        if(CurrentUser.getUsuario() != null){
            EditText login = (EditText) contentView.findViewById(R.id.txtLogin);
            EditText altura = (EditText) contentView.findViewById(R.id.textAltura);
            EditText nome = (EditText) contentView.findViewById(R.id.textNome);
            EditText dataNasc = (EditText) contentView.findViewById(R.id.textDtNascimento);
            EditText email = (EditText) contentView.findViewById(R.id.textEmail);
            EditText emailNutricionista = (EditText) contentView.findViewById(R.id.textEmailNutri);

            Spinner sexo = (Spinner) contentView.findViewById(R.id.spnSexo);
            Spinner raca = (Spinner) contentView.findViewById(R.id.spnRaca);
            Switch eNutricionista = (Switch) contentView.findViewById(R.id.swtNutricionista);

            login.setText(CurrentUser.getUsuario().getString("username"));
            nome.setText(CurrentUser.getUsuario().getString("nome"));
            altura.setText(CurrentUser.getUsuario().getNumber("altura").toString());
            dataNasc.setText(CurrentUser.getUsuario().getString("dtNascimento"));
            email.setText(CurrentUser.getUsuario().getString("email"));
            emailNutricionista.setText(CurrentUser.getUsuario().getString("email"));

            for (int i=0;i<sexo.getCount();i++){
                if (sexo.getItemAtPosition(i).toString().equalsIgnoreCase(CurrentUser.getUsuario().getString("sexo"))){
                    sexo.setSelection(i);
                    break;
                }
            }

            for (int i=0;i<sexo.getCount();i++){
                if (raca.getItemAtPosition(i).toString().equalsIgnoreCase(CurrentUser.getUsuario().getString("raca"))){
                    raca.setSelection(i);
                    break;
                }
            }

            eNutricionista.setChecked(Boolean.parseBoolean(CurrentUser.getUsuario().getString("nutricionista")));

        }

        return contentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
