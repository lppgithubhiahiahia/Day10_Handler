package com.example.lyp.day10_handler;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;


public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String title = intent.getStringExtra("title");
        if (title!=null){

            NotificationManager mannger = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            Intent intent1 = new Intent(context, Main2Activity.class);

            PendingIntent activity = PendingIntent.getActivity(context, 16, intent1, PendingIntent.FLAG_CANCEL_CURRENT);


            Notification notification = new NotificationCompat.Builder(context, "123")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("今天周五")
                    .setContentText(title)
                    .setAutoCancel(true)
                    .setContentIntent(activity)
                    .build();

            mannger.notify(1,notification);
        }
    }
}
