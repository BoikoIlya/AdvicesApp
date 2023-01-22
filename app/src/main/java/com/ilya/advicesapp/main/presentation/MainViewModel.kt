package com.ilya.advicesapp.main.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilya.advicesapp.R
import com.ilya.advicesapp.advices.presentation.DispatchersList
import com.ilya.advicesapp.dailynotification.AlarmManagerWrapper
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by HP on 15.11.2022.
 **/
@RequiresApi(Build.VERSION_CODES.M)
class MainViewModel @Inject constructor(
    private val navigationCommunication: NavigationCommunication.Mutable,
    private val alarmManagerWrapper: AlarmManagerWrapper,
    private val dispatchersList: DispatchersList
): ViewModel(), Communication.Collector<NavigationStrategy> {

    init {
            alarmManagerWrapper.start()
    }

    override suspend fun collect(
        lifecycleOwner: LifecycleOwner,
        collector: FlowCollector<NavigationStrategy>,
    )  = navigationCommunication.collect(lifecycleOwner, collector)

}