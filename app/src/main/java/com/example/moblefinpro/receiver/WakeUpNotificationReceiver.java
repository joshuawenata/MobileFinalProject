package com.example.moblefinpro.receiver;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.moblefinpro.R;

public class WakeUpNotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        CharSequence name = context.getString(R.string.hiro);
        String description = "Your Healthcare Buddy";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("channel_id", name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_id")
                .setSmallIcon(R.drawable.image1)
                .setContentTitle(context.getString(R.string.hiro_wake_up_reminder))
                .setContentText("Time To Wake Up :)")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(2, builder.build());
    }
}
