package com.ilya.advicesapp.workmanager

import android.content.Context
import androidx.work.*
import java.util.*
import java.util.concurrent.TimeUnit

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
            val targetTime = Calendar.getInstance()
            val timeNow = Calendar.getInstance()
            targetTime.apply {
                set(Calendar.HOUR_OF_DAY, 9)
                set(Calendar.MINUTE, 30)
                set(Calendar.SECOND, 0)
            }
            if(timeNow.after(targetTime))
                targetTime.add(Calendar.DAY_OF_MONTH,1)

            val delay = targetTime.timeInMillis - timeNow.timeInMillis

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
