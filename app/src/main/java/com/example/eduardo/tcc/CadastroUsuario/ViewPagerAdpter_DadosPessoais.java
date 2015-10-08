package com.example.eduardo.tcc.CadastroUsuario;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.eduardo.tcc.CadastroUsuario.Fragment_DadosPessoais;
import com.example.eduardo.tcc.CadastroUsuario.Fragment_FatoresRisco;

/**
 * Created by Eduardo on 14/08/2015.
 */
public class ViewPagerAdpter_DadosPessoais extends FragmentPagerAdapter {

    String[] tabtitlearray = {"Dados Pessoais", "Fatores de Risco"};

    public ViewPagerAdpter_DadosPessoais(android.support.v4.app.FragmentManager manager){
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0: return new Fragment_DadosPessoais();
            case 1: return new Fragment_FatoresRisco();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabtitlearray[position];
    }
}
