package com.example.eduardo.tcc.Nutricionista;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

//import com.example.eduardo.tcc.R;
import com.example.eduardo.tcc.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ClientesNutricionista extends Activity implements AdapterView.OnItemClickListener {

    private ListView listview;
    private List<ParseObject> queryClientes;
    String objectIdClienteSelecionado = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.clientes_nutricionista);

        queryClientes = new ArrayList<ParseObject>();
        ParseQuery<ParseObject> innerQuery = ParseQuery.getQuery("_User");
        innerQuery.whereEqualTo("idNutricionista", ParseObject.createWithoutData("_User", ParseUser.getCurrentUser().getObjectId()));
        //innerQuery.whereEqualTo("nutricionista", false);
        ArrayList<String> listaExibida = new ArrayList<String>();

        try {
            queryClientes = innerQuery.find();

            for(ParseObject parseObj : queryClientes){
                System.out.println("parseObj: " + parseObj.getObjectId());
                listaExibida.add(parseObj.get("nome").toString());
            }

            if(!listaExibida.isEmpty()) {
                listview = (ListView) findViewById(R.id.listView1);

                final StableArrayAdapter adapter = new StableArrayAdapter(this,
                        android.R.layout.simple_list_item_1, listaExibida);
                listview.setAdapter(adapter);

                listview.setOnItemClickListener(this);
            } else {
                Toast.makeText(ClientesNutricionista.this, "Sem clientes para exibir.", Toast.LENGTH_SHORT).show();
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }



    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("HelloListView", "You clicked Item: " + id + " at position:" + position + " - selected item: " );
        // Then you start a new Activity via Intent
        Intent intent = new Intent();
        intent.setClass(this, ListItemDetail.class);
        intent.putExtra("position", position);
        // Or / And
        System.out.println("onItemClick: id - "  + id);
        intent.putExtra("id", id);
        //intent.putExtra("objectIdClienteSelecionado" , objectIdClienteSelecionado);
        startActivity(intent);
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
}
