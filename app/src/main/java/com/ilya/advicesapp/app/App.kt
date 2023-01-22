package com.ilya.advicesapp.app

import android.app.Application
import com.ilya.advicesapp.advices.di.AdvicesModule
import com.ilya.advicesapp.dailynotification.di.AlarmManagerModule

/**
 * Created by HP on 06.12.2022.
 **/

class App: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .advicesModule(AdvicesModule(this))
            .alarmManagerModule(AlarmManagerModule(this))
            .build()
    }
}