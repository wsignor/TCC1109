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
        query.whereEqualTo("objectId", ParseUser.getCurrentUser().getObjectId());
        try {
            usuarios = query.find();
            if(usuarios != null){
                usuario = usuarios.get(0);
            }
        }catch (ParseException e){

        }
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
