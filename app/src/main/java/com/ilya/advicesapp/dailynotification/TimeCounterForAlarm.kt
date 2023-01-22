package com.ilya.advicesapp.dailynotification

import java.util.*

/**
 * Created by HP on 21.01.2023.
 **/
interface TimeCounterForAlarm {

    fun countTime(): Long

    class Base: TimeCounterForAlarm{

        override  fun countTime(): Long {
            val targetTime = Calendar.getInstance()
            val timeNow = Calendar.getInstance()
            targetTime.apply {
                set(Calendar.HOUR_OF_DAY, 8)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
            }
            if(timeNow.after(targetTime))
                targetTime.add(Calendar.DAY_OF_MONTH,1)

            return targetTime.timeInMillis
        }

    }
}
