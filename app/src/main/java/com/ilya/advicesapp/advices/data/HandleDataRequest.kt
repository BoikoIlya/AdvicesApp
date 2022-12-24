package com.ilya.advicesapp.advices.data

import com.ilya.advicesapp.advices.data.cache.CacheDataSource
import com.ilya.advicesapp.advices.domain.AdvicesDomain
import com.ilya.advicesapp.advices.domain.HandleError
import javax.inject.Inject

/**
 * Created by HP on 07.12.2022.
 **/
interface HandleDataRequest {

    suspend fun  handle(block:suspend ()->AdvicesDomain)

    class Base @Inject constructor(
        private val handleError: HandleError<Exception>,
        private val cacheDataSource: CacheDataSource
    ): HandleDataRequest{

        override suspend fun handle(block:suspend () -> AdvicesDomain)
            = try {
                    val result = block.invoke()
                    cacheDataSource.saveAdvices(result)
                } catch (e: Exception) {
                    throw handleError.handle(e)
                }

    }

}