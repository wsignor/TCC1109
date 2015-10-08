package com.example.eduardo.tcc.CadastroUsuario;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.eduardo.tcc.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by Eduardo on 14/08/2015.
 */
public class Fragment_FatoresRisco extends android.support.v4.app.Fragment {

    View contentView2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView2 = inflater.inflate(R.layout.fatores_risco, null);



        if(ParseUser.getCurrentUser() != null){
            Switch hipertensaoFamiliar = (Switch) contentView2.findViewById(R.id.swtHipertensaoFamilia);
            Switch diabetesFamiliar = (Switch) contentView2.findViewById(R.id.swtDiabetesFamilia);
            Switch cardiovascularFamiliar = (Switch) contentView2.findViewById(R.id.swtCardiovascularFamilia);
            Switch obesidadeFamiliar = (Switch) contentView2.findViewById(R.id.swtObesidadeFamilia);
            Switch sindromeFamiliar = (Switch) contentView2.findViewById(R.id.swtSindromeMetabolicaFamilia);
            Switch diabetico = (Switch) contentView2.findViewById(R.id.swtDiabetico);
            Switch hipertenso = (Switch) contentView2.findViewById(R.id.swtHipertenso);

            System.out.println("ParseUser.getCurrentUser().getUsername() " + ParseUser.getCurrentUser().getUsername() );
            ParseQuery innerQuery = new ParseQuery("_User");
            innerQuery.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
            ParseQuery<ParseObject> query = ParseQuery.getQuery("InformacoesImutaveis");
            query.whereMatchesQuery("idUsuario", innerQuery);

            try {
                ParseObject info = query.getFirst();
                hipertensaoFamiliar.setChecked(info.getBoolean("hipertensaoFamiliar"));
                diabetesFamiliar.setChecked(info.getBoolean("diabetesFamiliar"));
                cardiovascularFamiliar.setChecked(info.getBoolean("cardiovascularFamiliar"));
                obesidadeFamiliar.setChecked(info.getBoolean("obesidadeFamiliar"));
                sindromeFamiliar.setChecked(info.getBoolean("sindromeFamiliar"));
                hipertenso.setChecked(info.getBoolean("hipertenso"));
                diabetico.setChecked(info.getBoolean("diabetico"));
            } catch (ParseException e){
                System.out.println("e.getMessage()" + e.getMessage());
            }
        }

        return contentView2;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
