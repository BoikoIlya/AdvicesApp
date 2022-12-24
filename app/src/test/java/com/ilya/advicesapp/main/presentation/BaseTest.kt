package com.ilya.advicesapp.main.presentation

import androidx.lifecycle.LifecycleOwner
import com.ilya.advicesapp.advices.presentation.AdvicesUi
import com.ilya.advicesapp.advices.presentation.AdvicesCommunication
import com.ilya.advicesapp.advices.presentation.UiState
import kotlinx.coroutines.flow.FlowCollector

/**
 * Created by HP on 15.11.2022.
 **/
abstract class BaseTest {

    class TestNavigationCommunication: NavigationCommunication.Mutable{
        lateinit var strategy: NavigationStrategy

        override suspend fun map(newValue: NavigationStrategy) {
            strategy = newValue
        }

        override suspend fun collect(
            lifecycleOwner: LifecycleOwner,
            collector: FlowCollector<NavigationStrategy>,
        )  = Unit

    }

    class TestLanguageCommunication: AdvicesCommunication{
        val languageList = mutableListOf<AdvicesUi>()
        val progressList = mutableListOf<Int>()
        val stateList = mutableListOf<UiState>()

        override fun showProgress(show: Int) {
            progressList.add(show)
        }

        override fun showUiState(state: UiState) {
            stateList.add(state)
        }

        override fun showList(list: List<AdvicesUi>) {
            languageList.addAll(list)
        }

        override suspend fun collectProgress(owner: LifecycleOwner, collector: FlowCollector<Int>)= Unit
        override suspend fun collectState(owner: LifecycleOwner, collector: FlowCollector<UiState>, ) = Unit
        override suspend fun collectList(owner: LifecycleOwner, collector: FlowCollector<List<AdvicesUi>>, ) = Unit
    }
}