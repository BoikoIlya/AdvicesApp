package com.ilya.advicesapp.workmanager

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ilya.advicesapp.R
import com.ilya.advicesapp.main.presentation.MainActivity

class PeriodicNotification(
   private val context: Context,
    params: WorkerParameters
) :CoroutineWorker(context, params) {

        companion object{
            const val channelId = "channel_id_1"
            const val channelName = "channel_name_1"
            const val notificationId = 1
            const val requestCode = 0
        }

        private lateinit var channel:NotificationChannel

        @SuppressLint("InlinedApi")
        override suspend fun doWork(): Result {
            val notificationManager = NotificationManagerCompat.from(context)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel(
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

            return Result.success()
    }


}
