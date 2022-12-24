package com.ilya.advicesapp.advices.domain

import com.ilya.advicesapp.advices.data.cache.AdvicesCache
import com.ilya.advicesapp.advices.presentation.AdvicesUi
import javax.inject.Inject

/**
 * Created by HP on 15.11.2022.
 **/
data class AdvicesDomain(
   private val advices: String,
   private val date: String,
   private val searchTerm: String
){
    fun <T> map(mapper: Mapper<T>): T = mapper.map(advices, date, searchTerm)

    interface Mapper<T>{
        fun map(
            advices: String,
            date: String,
            searchTerm: String
        ): T

            class ToAdviceUi @Inject constructor(): Mapper<AdvicesUi>{
                override fun map(
                    advices: String,
                    date: String,
                    searchTerm: String
                ): AdvicesUi {
                    return AdvicesUi(
                        advices = advices,
                        date = date,
                        searchTerm = searchTerm
                    )
                }

            }

            class Matches(private val searchTerm: String): Mapper<Boolean>{
                override fun map(
                    advices: String,
                    date: String,
                    searchTerm: String
                ): Boolean {
                    return this.searchTerm == searchTerm
                }
            }


        class ToCacheMapper @Inject constructor(): Mapper<AdvicesCache>{
            override fun map(
                advices: String,
                date: String,
                searchTerm: String
            ): AdvicesCache {
                return AdvicesCache(
                    advices = advices,
                    date = date,
                    searchTerm = searchTerm,
                    time = System.currentTimeMillis()
                )
            }

        }
    }
}