package com.example.eduardo.tcc;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by Eduardo on 16/09/2015.
 */
public class CurrentUser {
    private static CurrentUser ourInstance;
    private static ParseObject usuario;

    private CurrentUser() {
        List<ParseObject> usuarios;

        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
        System.out.println(ParseUser.getCurrentUser().getObjectId());
        query.whereEqualTo("objectId", ParseUser.getCurrentUser().getObjectId());
        try {
            usuarios = query.find();
            if(usuarios != null){
                usuario = usuarios.get(0);
            }
        }catch (ParseException e){

        }
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    //List contain object with specific user id.
                    usuario = objects.get(0);
                }
            }
        });
    }

    public static CurrentUser getInstance() {
        return ourInstance;
    }

    public static void initCurrentUser(){
        if (ourInstance == null)
        {
            // Create the instance
            ourInstance = new CurrentUser();
        }
    }

    public static ParseObject getUsuario(){
        return usuario;
    }

}
