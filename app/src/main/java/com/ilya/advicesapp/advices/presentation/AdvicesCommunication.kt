package com.ilya.advicesapp.advices.presentation

import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.ilya.advicesapp.main.presentation.Communication
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

/**
 * Created by HP on 15.11.2022.
 **/
interface AdvicesCommunication: CollectLanguages{

    fun showProgress(show: Int)
    fun showUiState(state: UiState)
    fun showList(list: List<AdvicesUi>)

    class Base @Inject constructor(
        private val progressCommunication: ProgressCommunication,
        private val languageStateCommunication: AdviceStateCommunication,
        private val languageListCommunication: AdviceListCommunication
    ): AdvicesCommunication{
        override fun showProgress(show: Int) = progressCommunication.map(show)

        override fun showUiState(state: UiState) = languageStateCommunication.map(state)

        override fun showList(list: List<AdvicesUi>) = languageListCommunication.map(list)

        override suspend fun collectProgress(
            owner: LifecycleOwner,
            collector: FlowCollector<Int>,
        ) = progressCommunication.collect(owner, collector)

        override suspend fun collectState(
            owner: LifecycleOwner,
            collector: FlowCollector<UiState>,
        ) = languageStateCommunication.collect(owner,collector)

        override suspend fun collectList(
            owner: LifecycleOwner,
            collector: FlowCollector<List<AdvicesUi>>,
        ) = languageListCommunication.collect(owner, collector)

    }

}

interface CollectLanguages {

   suspend fun collectProgress(owner: LifecycleOwner, collector: FlowCollector<Int>)

   suspend fun collectState(owner: LifecycleOwner,  collector: FlowCollector<UiState>)

   suspend fun collectList(owner: LifecycleOwner,  collector: FlowCollector<List<AdvicesUi>>)
}

interface ProgressCommunication : Communication.Mutable<Int> {
    class Base @Inject constructor() : Communication.UiUpdate<Int>(View.GONE), ProgressCommunication
}

interface AdviceStateCommunication : Communication.Mutable<UiState> {
    class Base @Inject constructor(): Communication.UiUpdate<UiState>(UiState.Success), AdviceStateCommunication
}

interface AdviceListCommunication : Communication.Mutable<List<AdvicesUi>> {
    class Base @Inject constructor(): Communication.UiUpdate<List<AdvicesUi>>(emptyList()), AdviceListCommunication
}