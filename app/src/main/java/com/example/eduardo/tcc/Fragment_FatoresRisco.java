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

/**
 * Created by Eduardo on 14/08/2015.
 */
public class Fragment_FatoresRisco extends android.support.v4.app.Fragment {

    View contentView2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView2 = inflater.inflate(R.layout.fatores_risco, null);

        Spinner spinner;

        spinner = (Spinner) contentView2.findViewById( R.id.spnPaiMaeHipertensos );

        ArrayAdapter<CharSequence> adapterHipertensos = ArrayAdapter.createFromResource( getActivity(), R.array.sim_nao_array , android.R.layout.simple_spinner_item);
        adapterHipertensos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterHipertensos);

        spinner = (Spinner) contentView2.findViewById( R.id.spnDiabetesFamilia );

        ArrayAdapter<CharSequence> adapterDiabetes = ArrayAdapter.createFromResource( getActivity(), R.array.sim_nao_array , android.R.layout.simple_spinner_item);
        adapterDiabetes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterDiabetes);

        if(CurrentUser.getUsuario() != null){
            Switch diabetico = (Switch) contentView2.findViewById(R.id.swtDiabetico);
            Switch hipertenso = (Switch) contentView2.findViewById(R.id.swtHipertenso);
            diabetico.setChecked(Boolean.parseBoolean(CurrentUser.getUsuario().getString("diabetico")));
            hipertenso.setChecked(Boolean.parseBoolean(CurrentUser.getUsuario().getString("hipertenso")));
        }

        return contentView2;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
