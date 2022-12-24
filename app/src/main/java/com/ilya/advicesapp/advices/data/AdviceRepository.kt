package com.ilya.advicesapp.advices.data

import com.ilya.advicesapp.advices.data.cache.CacheDataSource
import com.ilya.advicesapp.advices.data.cloud.CloudDataSource
import com.ilya.advicesapp.advices.domain.AdvicesDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by HP on 25.11.2022.
 **/
interface AdviceRepository {

    suspend fun allAdvices(): Flow<List<AdvicesDomain>>

    suspend fun findAdvices(name: String)

    suspend fun randomAdvice()

    class Base @Inject constructor(
       private val cache: CacheDataSource,
       private val cloud: CloudDataSource,
       private val handleRequest: HandleDataRequest,
   ): AdviceRepository{

        override suspend fun allAdvices(): Flow<List<AdvicesDomain>> = cache.readAdvices()

        override suspend fun findAdvices(word: String){
                 handleRequest.handle {
                    val data = if (cache.containsWord(word)) cache
                    else cloud
                    data.advices(word)
                }
        }

        override suspend fun randomAdvice() = handleRequest.handle { cloud.randomAdvice() }
        }

}
