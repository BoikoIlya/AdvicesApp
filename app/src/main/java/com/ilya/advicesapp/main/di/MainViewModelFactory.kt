package com.ilya.advicesapp.main.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ilya.advicesapp.main.presentation.MainViewModel
import javax.inject.Inject
import javax.inject.Provider

/**
 * Created by HP on 07.12.2022.
 **/

class MainViewModelFactory @Inject constructor(
    provider: Provider<MainViewModel>
): ViewModelProvider.Factory {

    private val providers = mapOf<Class<*>, Provider<out ViewModel>>(
        MainViewModel::class.java to provider
    )

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return providers[modelClass]!!.get() as T
    }

}