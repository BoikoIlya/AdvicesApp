package com.ilya.advicesapp.main.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilya.advicesapp.R
import com.ilya.advicesapp.advices.presentation.DispatchersList
import com.ilya.advicesapp.workmanager.WorkManagerWrapper
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by HP on 15.11.2022.
 **/
class MainViewModel @Inject constructor(
    private val navigationCommunication: NavigationCommunication.Mutable,
    private val workManagerWrapper: WorkManagerWrapper
): ViewModel(), Communication.Collector<NavigationStrategy> {

    init {
        workManagerWrapper.start()
    }

    override suspend fun collect(
        lifecycleOwner: LifecycleOwner,
        collector: FlowCollector<NavigationStrategy>,
    )  = navigationCommunication.collect(lifecycleOwner, collector)

}