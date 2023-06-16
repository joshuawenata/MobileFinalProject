package com.example.moblefinpro;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class notificationReceiver extends BroadcastReceiver {

    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {

        // Get id and activity from intent
        String notificationType = intent.getStringExtra("notifType");
        String message = intent.getStringExtra("message");

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        Intent i = new Intent(context, eatreminder.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new NotificationCompat.Builder(context, notificationType)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(notificationType + " Reminder")
                .setContentText("It's time for " + message + "!")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setPriority(NotificationManagerCompat.IMPORTANCE_HIGH)
                .setContentIntent(contentIntent)
                .build();

        notificationManager.notify(1, notification);

    }
}
