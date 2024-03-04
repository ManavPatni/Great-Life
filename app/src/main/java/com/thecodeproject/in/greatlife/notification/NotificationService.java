package com.thecodeproject.in.greatlife.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.thecodeproject.in.greatlife.MainActivity;
import com.thecodeproject.in.greatlife.R;

import java.util.Objects;

public class NotificationService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        pushNotification(Objects.requireNonNull(message.getNotification()).getTitle(), message.getNotification().getBody());

    }

    private void pushNotification(String title, String msg) {

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Notification notification;

        final String CHANNEL_ID = "general";

        Intent iNotify = new Intent(this, MainActivity.class);
        iNotify.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,100, iNotify, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "General";
            String description = "General notification channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            if (notificationManager != null){
                notificationManager.createNotificationChannel(channel);
            }

            notification = new Notification.Builder(this)
                    .setSmallIcon(R.drawable.ic_happy)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setContentTitle(title)
                    .setSubText(msg)
                    .build();

        } else {

            notification = new Notification.Builder(this)
                    .setSmallIcon(R.drawable.ic_happy)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setContentTitle(title)
                    .setSubText(msg)
                    .build();

        }

        if (notificationManager != null){
            notificationManager.notify(1,notification);
        }

    }

}
