package com.myapps.materialapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ozhar on 08-03-2016.
 */
public class AlarmReceiver extends BroadcastReceiver {
    private static final int MY_NOTIFICATION_ID=1;
    NotificationManager notificationManager;
    Notification myNotification;
    String medname, medtime, medintake;

    @Override
    public void onReceive(Context context, Intent intent) {
        medname = intent.getStringExtra("medicinename");
        medtime = intent.getStringExtra("medicinetime");
        medintake = intent.getStringExtra("medicineintake");
        Log.i("DATA", medname);
        Toast.makeText(context, "Alarm received! for " + medname, Toast.LENGTH_LONG).show();
        Intent myIntent = new Intent(context,NotifyActivity.class);
        myIntent.putExtra("mname", medname);
        myIntent.putExtra("mtime", medtime);
        myIntent.putExtra("mintake", medintake);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                myIntent,
                Intent.FLAG_ACTIVITY_NEW_TASK);

        myNotification = new NotificationCompat.Builder(context)
                .setContentTitle("Medicine Notification")
                .setContentText("Take your Medicine")
                .setTicker("Notification!")
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();

        notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(MY_NOTIFICATION_ID, myNotification);
    }

}

