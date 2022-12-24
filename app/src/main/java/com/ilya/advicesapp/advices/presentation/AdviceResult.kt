package com.ilya.advicesapp.advices.presentation

import com.ilya.advicesapp.advices.domain.AdvicesDomain

sealed interface AdviceResult {

    interface Mapper<T>{
        fun map(list: List<AdvicesDomain>, errorMessage: String):T
    }

     fun <T> map(mapper: Mapper<T>):T

    data class Success(private val list: List<AdvicesDomain> = emptyList()): AdviceResult{
        override fun <T> map(mapper: Mapper<T>): T  = mapper.map(list, "")
    }

    data class Error(private val message: String): AdviceResult{
        override fun <T> map(mapper: Mapper<T>): T  = mapper.map(emptyList(), message)
    }

    object Loading: AdviceResult{
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(emptyList(),"")
    }
}
