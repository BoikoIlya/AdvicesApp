package com.ilya.advicesapp.dailynotification

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.ilya.advicesapp.app.App
import javax.inject.Inject

/**
 * Created by HP on 21.01.2023.
 **/
class BootUpReceiver: BroadcastReceiver() {

    @Inject
    lateinit var alarmManagerWrapper: AlarmManagerWrapper

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context?, intent: Intent?) {
       if(intent!!.action != "android.intent.action.BOOT_COMPLETED") return

        (context as App).appComponent.inject(this)

        alarmManagerWrapper.start()
    }
}