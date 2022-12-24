package com.ilya.advicesapp.advices.domain

import com.ilya.advicesapp.advices.data.AdviceRepository
import com.ilya.advicesapp.advices.presentation.AdviceResult
import kotlinx.coroutines.flow.*
import javax.inject.Inject

interface HandleRequest {

    suspend fun handle(block: suspend()-> Unit): Flow<AdviceResult>

    class Base @Inject constructor(
        private val handlerError: HandleError<String>,
        private val repository: AdviceRepository
    ): HandleRequest{
        override suspend fun handle(block: suspend () -> Unit): Flow<AdviceResult>
        = flow {
            emit(AdviceResult.Loading)
            emit(
            try {
                block.invoke()
                AdviceResult.Success(repository.allAdvices().first())
            } catch (e: Exception) {
               AdviceResult.Error(handlerError.handle(e))
            })
        }
    }
}
