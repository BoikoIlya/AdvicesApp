package com.ilya.advicesapp.workmanager.di

import android.content.Context
import com.ilya.advicesapp.workmanager.WorkManagerWrapper
import dagger.Module
import dagger.Provides

/**
 * Created by HP on 24.12.2022.
 **/
@Module
 class WorkManagerModule(private val context: Context) {

    @Provides
    fun provideWorkManagerWrapper(): WorkManagerWrapper{
        return WorkManagerWrapper.Base(context)
    }

}
