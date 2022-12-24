package com.ilya.advicesapp.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import com.ilya.advicesapp.R
import com.ilya.advicesapp.main.presentation.MainActivity
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by HP on 23.12.2022.
 **/
interface WorkManagerWrapper {

    fun start()

    class Base(private val  context: Context): WorkManagerWrapper{

        private val workManager = WorkManager.getInstance(context)

        companion object{
            const val workName = "work_name"
        }

        override fun start() {
            val calendar = Calendar.getInstance()
            val timeNow = calendar.timeInMillis
            calendar.apply {
                set(Calendar.HOUR_OF_DAY, 9)
                set(Calendar.MINUTE, 30)
                set(Calendar.SECOND, 0)
            }
            if(calendar[Calendar.HOUR_OF_DAY]>9 && calendar[Calendar.MINUTE]>30)
                calendar.add(Calendar.DAY_OF_MONTH,1)

            val delay = calendar.timeInMillis - timeNow

                val request = PeriodicWorkRequestBuilder<PeriodicNotification>(
                    24,
                    TimeUnit.HOURS
                ).setInitialDelay(delay, TimeUnit.MILLISECONDS)
                    .build()
                workManager.enqueueUniquePeriodicWork(
                    workName,
                    ExistingPeriodicWorkPolicy.KEEP,
                    request
                )
            }
        }

    }
