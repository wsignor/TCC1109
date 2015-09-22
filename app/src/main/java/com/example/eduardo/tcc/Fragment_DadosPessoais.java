package com.example.eduardo.tcc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by Eduardo on 14/08/2015.
 */
public class Fragment_DadosPessoais extends android.support.v4.app.Fragment {

    View contentView;
    EditText emailNutricionista;
    Switch eNutricionista;

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

        EditText dataNasc = (EditText) contentView.findViewById(R.id.textDtNascimento);
        dataNasc.addTextChangedListener(Mask.insert("##/##/####", dataNasc));

        eNutricionista = (Switch) contentView.findViewById(R.id.swtNutricionista);
        emailNutricionista = (EditText) contentView.findViewById(R.id.textEmailNutri);
        eNutricionista.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (eNutricionista.isChecked()) {
                    emailNutricionista.setVisibility(View.INVISIBLE);
                }else{
                    emailNutricionista.setVisibility(View.VISIBLE);
                }
            }
        });

        if(ParseUser.getCurrentUser() != null){
            EditText login = (EditText) contentView.findViewById(R.id.txtLogin);
            EditText altura = (EditText) contentView.findViewById(R.id.textAltura);
            EditText nome = (EditText) contentView.findViewById(R.id.textNome);
            dataNasc = (EditText) contentView.findViewById(R.id.textDtNascimento);
            EditText email = (EditText) contentView.findViewById(R.id.textEmail);
            emailNutricionista = (EditText) contentView.findViewById(R.id.textEmailNutri);

            Spinner sexo = (Spinner) contentView.findViewById(R.id.spnSexo);
            Spinner raca = (Spinner) contentView.findViewById(R.id.spnRaca);
            eNutricionista = (Switch) contentView.findViewById(R.id.swtNutricionista);

            login.setText(ParseUser.getCurrentUser().getString("username"));
            nome.setText(ParseUser.getCurrentUser().getString("nome"));
            altura.setText(ParseUser.getCurrentUser().getNumber("altura").toString());
            dataNasc.setText(ParseUser.getCurrentUser().getString("dtNascimento"));
            email.setText(ParseUser.getCurrentUser().getString("email"));


            // pegar email nutri
            ParseObject nutricionista = ParseUser.getCurrentUser().getParseObject("idNutricionista");
            if(nutricionista != null){
                ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
                query.whereEqualTo("nutricionista", true);
                query.whereEqualTo("objectId", ParseUser.getCurrentUser().getParseObject("idNutricionista").getObjectId());
                query.getFirstInBackground(new GetCallback<ParseObject>() {
                    public void done(ParseObject object, com.parse.ParseException e) {
                        if (object == null) {
                            Log.d("score", "Failed to get idNutricionista.");
                        } else {
                            Log.d("score", "idNutricionista retrieved ok.");
                            System.out.println("objectId nutri: " + object.getObjectId().toString());
                            emailNutricionista.setText(object.get("email").toString());
                        }
                    }
                });
            }


            for (int i=0;i<sexo.getCount();i++){
                if (sexo.getItemAtPosition(i).toString().equalsIgnoreCase(ParseUser.getCurrentUser().getString("sexo"))){
                    sexo.setSelection(i);
                    break;
                }
            }

            for (int i=0;i<raca.getCount();i++){
                if (raca.getItemAtPosition(i).toString().equalsIgnoreCase(ParseUser.getCurrentUser().getString("raca"))){
                    raca.setSelection(i);
                    break;
                }
            }

            eNutricionista.setChecked(Boolean.parseBoolean(ParseUser.getCurrentUser().getString("nutricionista")));

        }

        return contentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
