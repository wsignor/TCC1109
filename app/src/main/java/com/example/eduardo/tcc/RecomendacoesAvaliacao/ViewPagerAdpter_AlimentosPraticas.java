package com.example.eduardo.tcc.RecomendacoesAvaliacao;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Eduardo on 14/08/2015.
 */
public class ViewPagerAdpter_AlimentosPraticas extends FragmentPagerAdapter {

    String[] tabtitlearray = {"Recomendações Nutricionais", "Hábitos"};

    public ViewPagerAdpter_AlimentosPraticas(android.support.v4.app.FragmentManager manager){
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0: return new Fragment_Recomendacoes_Nutricionais();
            case 1: return new Fragment_Habitos();
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
