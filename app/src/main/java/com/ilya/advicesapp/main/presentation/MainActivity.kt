package com.ilya.advicesapp.main.presentation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.graphics.createBitmap
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ilya.advicesapp.R
import com.ilya.advicesapp.app.App
import com.ilya.advicesapp.core.EspressoIdlingResource
import com.ilya.advicesapp.main.di.MainViewModelFactory
import com.ilya.advicesapp.workmanager.PeriodicNotification
import com.ilya.advicesapp.workmanager.WorkManagerWrapper
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (this.application as App).appComponent.inject(this)

        val viewModel =  ViewModelProvider(this, viewModelFactory)
            .get(MainViewModel::class.java)

        if (savedInstanceState==null)
            NavigationStrategy.Replace(Screen.Languages)
                .navigate(supportFragmentManager, R.id.container)

            lifecycleScope.launch {
                viewModel.collect(this@MainActivity) { navigationStrategy ->
                    navigationStrategy.navigate(supportFragmentManager, R.id.container)
                }
            }


    }



}