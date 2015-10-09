package com.example.eduardo.tcc.RecomendacoesAvaliacao;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.eduardo.tcc.CadastroUsuario.ViewPagerAdpter_DadosPessoais;
import com.example.eduardo.tcc.R;
import com.parse.ParseUser;

/**
 * Created by Eduardo on 08/10/2015.
 */
public class AlimentosPraticas extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    ParseUser user;
    EditText emailNutricionista;
    Switch eNutricionista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alimentos_praticas);

        Intent intent = getIntent();
        String idDoenca = intent.getStringExtra("idDoenca");

        System.out.println("idDoenca = " + idDoenca);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        ViewPagerAdpter_AlimentosPraticas viewPagerAdpterAlimentosPraticas = new ViewPagerAdpter_AlimentosPraticas(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdpterAlimentosPraticas);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
