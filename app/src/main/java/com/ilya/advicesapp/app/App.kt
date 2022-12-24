package com.ilya.advicesapp.app

import android.app.Application
import com.ilya.advicesapp.advices.di.AdvicesModule
import com.ilya.advicesapp.workmanager.di.WorkManagerModule

/**
 * Created by HP on 06.12.2022.
 **/

class App: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .advicesModule(AdvicesModule(this))
            .workManagerModule(WorkManagerModule(this))
            .build()
    }
}