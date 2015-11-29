package com.example.eduardo.tcc.Notification;


import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.eduardo.tcc.Notification.NotifyService;

/**
 * Set an alarm for the date passed into the constructor
 * When the alarm is raised it will start the NotifyService
 *
 * This uses the android build in alarm manager *NOTE* if the phone is turned off this alarm will be cancelled
 *
 * This will run on it's own thread.
 * Created by Wagner on 21/11/2015.
 */
public class AlarmTask implements Runnable{
    // The date selected for the alarm
    private final Calendar date;
    // The android system alarm manager
    private final AlarmManager am;
    // Your context to retrieve the alarm manager from
    private final Context context;

    public AlarmTask(Context context, Calendar date) {
        this.context = context;
        this.am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        this.date = date;
    }

    @Override
    public void run() {
        // Request to start are service when the alarm date is upon us
        // We don't start an activity as we just want to pop up a notification into the system bar not a full activity
        Intent intent = new Intent(context, NotifyService.class);
        intent.putExtra(NotifyService.INTENT_NOTIFY, true);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);

        System.out.println("date to string - " + date.toString());
        System.out.println("date get seconds to string - " + date.getTime().getSeconds());
        System.out.println("date get day of the month - " + date.get(Calendar.DAY_OF_MONTH));
        System.out.println("date month - " + date.get(Calendar.MONTH));

        // Sets an alarm - note this alarm will be lost if the phone is turned off and on again
        am.set(AlarmManager.RTC, date.getTimeInMillis(), pendingIntent);
    }
}
