package com.praveen.weatherhub.scheduler

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.praveen.weatherhub.R
import com.praveen.weatherhub.ui.CurrentWeatherActivity
import com.praveen.weatherhub.utils.AppConstants.WEATHER_UPDATE_NOTIFICATION

object WeatherNotificationUtil {

    private val CHANNEL_ID = "weather_hub_channel_id"
    private val NOTIFICATION_ID = 101
    private val REQUEST_CODE = 0

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (notificationManager.getNotificationChannel(CHANNEL_ID) == null) {
                val channel = NotificationChannel(CHANNEL_ID, context.getString(R.string.notification_channel_name), IMPORTANCE_HIGH)
                channel.description = context.getString(R.string.notification_channel_description)
                channel.enableVibration(true)
                notificationManager.createNotificationChannel(channel)
            }
        }
    }


    fun generateNotification(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(context)
            val notificationText = context.getString(R.string.notification_text)
            val notificationIntent: PendingIntent
            val resultIntent = Intent(context, CurrentWeatherActivity::class.java)
            resultIntent.action = WEATHER_UPDATE_NOTIFICATION
            notificationIntent = PendingIntent.getActivity(context, REQUEST_CODE, resultIntent, PendingIntent.FLAG_ONE_SHOT)

            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.notification)
                    .setContentTitle(context.getString(R.string.notification_update))
                    .setContentText(notificationText)
                    .setTicker(context.getString(R.string.notification_channel_ticker))
                    .setStyle(NotificationCompat.BigTextStyle().bigText(notificationText))
                    .setContentIntent(notificationIntent)
                    .setTicker(notificationText)
                    .setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .addAction(R.drawable.notification_bg_normal, "show", notificationIntent)

            val notification = builder.build()
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(NOTIFICATION_ID, notification)
        }
    }
}

