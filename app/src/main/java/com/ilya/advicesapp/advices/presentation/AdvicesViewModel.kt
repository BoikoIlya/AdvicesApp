package com.ilya.advicesapp.advices.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilya.advicesapp.advices.domain.AdviceInteractor
import com.ilya.advicesapp.main.presentation.NavigationCommunication
import com.ilya.advicesapp.main.presentation.NavigationStrategy
import com.ilya.advicesapp.main.presentation.Screen
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by HP on 15.11.2022.
 **/
interface AdvicesViewModel: FetchData, CollectLanguages, ClearError {

    fun showDetails(item: AdvicesUi)

    fun randomAdvice()


    class Base @Inject constructor(
        private val handleResult: HandleAdviceResult,
        private val interactor: AdviceInteractor,
        private val adviceCommunication: AdvicesCommunication,
        private val navigationCommunication: NavigationCommunication.Mutable,
        private val dispatchersList: DispatchersList
    ): AdvicesViewModel, ViewModel(){

        init {
            handleResult.handle(viewModelScope){
                interactor.init()
            }
        }



        override fun showDetails(item: AdvicesUi) {
            viewModelScope.launch(dispatchersList.io()) {
                interactor.saveDetails(item)
                navigationCommunication.map(NavigationStrategy.Add(Screen.Details))
            }
        }

        override fun randomAdvice() = handleResult.handle(viewModelScope) {
            interactor.randomAdvice()
        }

        override fun findAdvices(name: String) {
            if(name.isEmpty())
                adviceCommunication.showUiState(UiState.ShowError("Enter word"))
            else handleResult.handle(viewModelScope){
                interactor.findAdvices(name)
            }
        }

        override suspend fun collectProgress(
            owner: LifecycleOwner,
            collector: FlowCollector<Int>,
        ) = adviceCommunication.collectProgress(owner,collector)

        override suspend fun collectState(
            owner: LifecycleOwner,
            collector: FlowCollector<UiState>,
        ) = adviceCommunication.collectState(owner, collector)

        override suspend fun collectList(
            owner: LifecycleOwner,
            collector: FlowCollector<List<AdvicesUi>>,
        ) = adviceCommunication.collectList(owner, collector)

        override fun clearError() {
            adviceCommunication.showUiState(UiState.ClearError())
        }

    }

}

interface FetchData {

    fun findAdvices(name: String)
}


interface ClearError {
    fun clearError()
}