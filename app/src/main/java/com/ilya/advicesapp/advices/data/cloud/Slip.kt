package com.ilya.advicesapp.advices.data.cloud

import javax.inject.Inject

data class Slip(
   private val advice: String,
   private val date: String,
   private val id: Int
){
    fun <T>map(mapper: Mapper<T>) = mapper.map(advice, date, id)

    interface Mapper<T>{
        fun map(
            advice: String,
            date: String,
            id: Int
        ): T



        class Advice @Inject constructor(): Mapper<String>{
            override fun map(advice: String, date: String, id: Int): String {
                return advice
            }

        }
    }
}