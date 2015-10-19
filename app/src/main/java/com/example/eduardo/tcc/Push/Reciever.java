package com.example.eduardo.tcc.Push;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.eduardo.tcc.Inicio.Inicial;
import com.parse.ParsePushBroadcastReceiver;

/**
 * Created by Wagner on 10/09/2015.
 */
public class Reciever extends ParsePushBroadcastReceiver {
    @Override
    protected void onPushReceive(Context context, Intent intent) {
        System.out.println("onPushReceive - custom receiver class - push received");
        super.onPushReceive(context, intent);
    }

    @Override
    protected void onPushOpen(Context context, Intent intent) {
        System.out.println("onPushOpen - custom receiver class - push clicked");
        System.out.println("com.parse.data"  +  intent.getExtras().getString("com.parse.Data"));

        Intent i = new Intent(context, Inicial.class);
        i.putExtras(intent.getExtras());
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
