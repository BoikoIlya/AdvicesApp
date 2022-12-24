package com.ilya.advicesapp.advices.presentation

import android.view.View
import com.ilya.advicesapp.advices.domain.AdvicesDomain
import com.ilya.advicesapp.core.EspressoIdlingResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface HandleAdviceResult {

    fun handle(
        coroutineScope: CoroutineScope,
        block: suspend()-> Flow<AdviceResult>
        )



    class Base @Inject constructor(
        private val communication: AdvicesCommunication,
        private val dispatchersList: DispatchersList,
        private val mapper: AdviceResult.Mapper<Unit>
    ): HandleAdviceResult{
        override fun handle(
            coroutineScope: CoroutineScope,
            block: suspend()->Flow<AdviceResult>
        ) {
                coroutineScope.launch(dispatchersList.io()) {
                 block.invoke().collect{ result->
                     if(result is AdviceResult.Loading) communication.showProgress(View.VISIBLE)
                     else {
                         result.map(mapper)
                         communication.showProgress(View.GONE)
                         EspressoIdlingResource.decrement()
                     }
                 }
            }
        }

    }
}

class AdviceResultMapper @Inject constructor(
    private val adviceCommunication: AdvicesCommunication,
    private val mapper: AdvicesDomain.Mapper<AdvicesUi>
): AdviceResult.Mapper<Unit>{

    override fun map(list: List<AdvicesDomain>, errorMessage: String) {
        adviceCommunication.showUiState(
            if(list.isNotEmpty()) {
                adviceCommunication.showList(list.map { it.map(mapper) })
                UiState.Success
            }
            else UiState.ShowError(errorMessage)
        )
    }
}

