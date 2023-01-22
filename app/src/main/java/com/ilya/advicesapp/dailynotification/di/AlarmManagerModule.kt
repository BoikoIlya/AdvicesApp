package com.ilya.advicesapp.dailynotification.di

import android.content.Context
import com.ilya.advicesapp.dailynotification.AlarmManagerWrapper
import com.ilya.advicesapp.dailynotification.TimeCounterForAlarm
import dagger.Module
import dagger.Provides

/**
 * Created by HP on 24.12.2022.
 **/
@Module
 class AlarmManagerModule(private val context: Context) {

    @Provides
    fun provideAlarmManagerWrapper(): AlarmManagerWrapper{
        return AlarmManagerWrapper.Base(context, TimeCounterForAlarm.Base())
    }

}
