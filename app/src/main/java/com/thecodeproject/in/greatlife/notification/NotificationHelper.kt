package com.thecodeproject.in.greatlife.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build

class NotificationHelper(context: Context, mood: String) {
    private val CHANNEL_ID = "mood notification"
    private val NOTIFICATION_ID = 154

    private fun moodNotification() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            val name = CHANNEL_ID
            val description = "Notification based on mood of user"
            val
        }
    }

}