package com.ilya.advicesapp.advices.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ilya.advicesapp.advices.presentation.AdvicesViewModel
import javax.inject.Inject
import javax.inject.Provider

/**
 * Created by HP on 06.12.2022.
 **/

class AdvicesViewModelFactory @Inject constructor(
    provider: Provider<AdvicesViewModel.Base>
): ViewModelProvider.Factory {

    private val providers = mapOf<Class<*>, Provider<out ViewModel>>(
        AdvicesViewModel.Base::class.java to provider
    )

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return providers[modelClass]!!.get() as T
    }

}



