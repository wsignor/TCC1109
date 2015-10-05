package com.example.eduardo.tcc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.clientes_nutricionista);


        queryClientes = new ArrayList<ParseObject>();
        ParseQuery<ParseObject> innerQuery = ParseQuery.getQuery("_User");
        //innerQuery.whereEqualTo("idNutricionista", ParseObject.createWithoutData("_User", ParseUser.getCurrentUser().getObjectId()));
        innerQuery.whereEqualTo("nutricionista", false);
        ArrayList<String> listaExibida = new ArrayList<String>();

        try {
            queryClientes = innerQuery.find();

            for(ParseObject parseObj : queryClientes){
                System.out.println("parseObj: " + parseObj.getObjectId());
                listaExibida.add(parseObj.get("nome").toString());
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        listview = (ListView) findViewById(R.id.listView1);
        /*String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile" };*/

        /*final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }*/
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, listaExibida);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(this);

        /*listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                view.animate().setDuration(2000).alpha(0)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                list.remove(item);
                                adapter.notifyDataSetChanged();
                                view.setAlpha(1);
                            }
                        });
            }

        });
        */
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("HelloListView", "You clicked Item: " + id + " at position:" + position + " - selected item: " );
        // Then you start a new Activity via Intent
        Intent intent = new Intent();
        intent.setClass(this, ListItemDetail.class);
        intent.putExtra("position", position);
        // Or / And
        intent.putExtra("id", id);
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
