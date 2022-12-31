package com.ilya.advicesapp.advices.domain

import com.ilya.advicesapp.advicedetails.data.AdviceDetails
import com.ilya.advicesapp.advices.data.AdviceRepository
import com.ilya.advicesapp.advices.presentation.AdviceResult
import com.ilya.advicesapp.advices.presentation.AdvicesUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by HP on 15.11.2022.
 **/
interface AdviceInteractor {

    fun saveDetails(item: AdvicesUi)

    suspend fun randomAdvice():Flow<AdviceResult>

    suspend fun init(): Flow<AdviceResult>

    suspend fun findAdvices(name: String): Flow<AdviceResult>

    class Base @Inject constructor(
        private val adviceDetails: AdviceDetails.Save<AdvicesUi>,
        private val repository: AdviceRepository,
        private val handleRequest: HandleRequest
    ): AdviceInteractor{
        override fun saveDetails(item: AdvicesUi) = adviceDetails.save(item)


        override suspend fun randomAdvice() = handleRequest.handle { repository.randomAdvice() }

        override suspend fun init(): Flow<AdviceResult> = handleRequest.handle{}

        override suspend fun findAdvices(name: String): Flow<AdviceResult>
        = handleRequest.handle { repository.findAdvices(name) }

    }
}