package com.ilya.advicesapp.dailynotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ilya.advicesapp.R
import com.ilya.advicesapp.main.presentation.MainActivity

/**
 * Created by HP on 21.01.2023.
 **/
class NotificationAlarmReceiver:BroadcastReceiver() {

    private lateinit var channel: NotificationChannel


    override fun onReceive(context: Context, intent: Intent?) {

            val notificationManager = NotificationManagerCompat.from(context)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                channel = NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_HIGH
                )
                notificationManager.createNotificationChannel(channel)
            }

            val pendingIntent = PendingIntent.getActivity(
                context,
                requestCode,
                Intent(context, MainActivity::class.java),
                PendingIntent.FLAG_IMMUTABLE
            )

            val builder = NotificationCompat.Builder(
                context,
                channelId
            )

            builder.setContentTitle(context.getString(R.string.notification_title))
                .setContentText(context.getString(R.string.notification_content))
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)

            notificationManager.notify(notificationId, builder.build())

        }

    companion object{
        const val channelId = "channel_id_1"
        const val channelName = "channel_name_1"
        const val notificationId = 1
        const val requestCode = 0
    }
    }

