package com.ilya.advicesapp.advicedetails.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ilya.advicesapp.advicedetails.presentation.DetailsViewModel
import javax.inject.Inject
import javax.inject.Provider

/**
 * Created by HP on 07.12.2022.
 **/

class DetailsViewModelFactory @Inject constructor(
    provider: Provider<DetailsViewModel>
): ViewModelProvider.Factory {

    private val providers = mapOf<Class<*>, Provider<out ViewModel>>(
        DetailsViewModel::class.java to provider
    )

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return providers[modelClass]!!.get() as T
    }

}
