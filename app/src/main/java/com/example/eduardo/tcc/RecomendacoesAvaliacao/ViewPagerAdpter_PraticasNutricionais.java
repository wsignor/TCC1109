package com.example.eduardo.tcc.RecomendacoesAvaliacao;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Eduardo on 14/08/2015.
 */
public class ViewPagerAdpter_PraticasNutricionais extends FragmentPagerAdapter {

    String[] tabtitlearray = {"Praticas Recomendadas", "Orientações Nutricionais"};

    public ViewPagerAdpter_PraticasNutricionais(android.support.v4.app.FragmentManager manager){
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0: return new Fragment_Praticas();
            case 1: return new Fragment_Nutricionais();
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
