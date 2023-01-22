package com.ilya.advicesapp.dailynotification

import android.app.AlarmManager
import android.app.AlarmManager.RTC
import android.app.AlarmManager.RTC_WAKEUP
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

/**
 * Created by HP on 21.01.2023.
 **/
@RequiresApi(Build.VERSION_CODES.M)
interface AlarmManagerWrapper {

    fun start()

    class Base(
        private val context: Context,
        private val delayCounterForAlarm: TimeCounterForAlarm
    ): AlarmManagerWrapper{


        private val alarm = context.getSystemService(AlarmManager::class.java)

        override  fun start() {

            val intent = Intent(context, NotificationAlarmReceiver::class.java)

            alarm.setRepeating(
                RTC_WAKEUP,
                delayCounterForAlarm.countTime(),
                AlarmManager.INTERVAL_DAY,
                PendingIntent.getBroadcast(
                    context,
                    0,
                    intent,
                 PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
        }

    }

}