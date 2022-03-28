package com.example.a15017498.app1_task_manager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

/**
 * Created by 15017498 on 9/11/2016.
 */
public class Notification_receiever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String name = prefs.getString("task_name","name not found");

        Intent repeating_intent = new Intent(context,MainActivity.class);
        // Clears the old activity, if there's 1 ontop of it (So it would display only 1 notification from that app)
            repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,100,repeating_intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(android.R.drawable.ic_menu_info_details)
                .setContentTitle(name)
                .setContentText("Click for more Info")
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_ALL)
                .setAutoCancel(true);

            notificationManager.notify(100,builder.build());

    }


}
