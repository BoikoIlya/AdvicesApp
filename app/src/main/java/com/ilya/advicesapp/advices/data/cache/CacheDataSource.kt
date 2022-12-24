package com.ilya.advicesapp.advices.data.cache

import com.ilya.advicesapp.advices.data.FetchLanguage
import com.ilya.advicesapp.advices.domain.AdvicesDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface CacheDataSource: FetchLanguage<AdvicesDomain> {

    suspend fun readAdvices(): Flow<List<AdvicesDomain>>

    suspend fun containsWord(word: String):Boolean

    fun saveAdvices(item:AdvicesDomain)

    class Base @Inject constructor(
        private val dao: AdviceDao,
        private val toCacheMapper: AdvicesDomain.Mapper<AdvicesCache>
    ):CacheDataSource{
        override suspend fun readAdvices(): Flow<List<AdvicesDomain>> =
            dao.readAllHistory().map { list-> list.map { item->
                AdvicesDomain(item.advices,item.date,item.searchTerm)
            } }

        override suspend fun containsWord(name: String): Boolean {
            return dao.historyItem(name)!=null
        }


        override fun saveAdvices(item: AdvicesDomain) {
            dao.saveAdviceItem(item.map(toCacheMapper))
        }

        override suspend fun advices(name: String): AdvicesDomain {
            val result = dao.historyItem(name)?: throw NoSuchElementException()

            return AdvicesDomain(result.advices,result.date,result.searchTerm)
        }

    }
}
