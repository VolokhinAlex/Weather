package com.example.java.android1.weather.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessaging : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val remoteDataMessage = remoteMessage.data
        if (remoteDataMessage.isNotEmpty()) {
            onHandleDataMessage(remoteDataMessage.toMap())
        } else {
            remoteMessage.notification?.let {
                val title = it.title
                val message = it.body
                if (title != null && message != null) {
                    onShowNotification(title, message)
                }
            }
        }
    }

    private fun onHandleDataMessage(data: Map<String, String>) {
        val title = data[PUSH_KEY_TITLE]
        val message = data[PUSH_KEY_MESSAGE]
        if (!title.isNullOrBlank() && !message.isNullOrBlank()) {
            onShowNotification(title, message)
        }
    }

    private fun onShowNotification(title: String, message: String) {
        val notificationBuilder = NotificationCompat.Builder(applicationContext, CHANNEL_ID).apply {
            setContentTitle(title)
            setContentText(message)
            setSmallIcon(androidx.core.R.drawable.notification_icon_background)
            priority = NotificationCompat.PRIORITY_DEFAULT
        }
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(
        notificationManager:
        NotificationManager
    ) {
        val name = "Weather"
        val descriptionChannel = "weather channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionChannel
        }
        notificationManager.createNotificationChannel(channel)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    companion object {
        private const val PUSH_KEY_TITLE = "title"
        private const val PUSH_KEY_MESSAGE = "message"
        private const val CHANNEL_ID = "weather_channel"
        private const val NOTIFICATION_ID = 1
    }
}