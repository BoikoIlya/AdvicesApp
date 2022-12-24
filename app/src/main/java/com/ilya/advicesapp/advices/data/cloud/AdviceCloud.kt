package com.ilya.advicesapp.advices.data.cloud

import android.annotation.SuppressLint
import com.ilya.advicesapp.advices.domain.AdvicesDomain
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

data class AdviceCloud(
    val query: String,
    val slips: List<Slip>,
    val total_results: String
){

    fun <T>map(mapper: Mapper<T>): T = mapper.map(query, slips, total_results)

    interface Mapper<T>{
        fun map(
             query: String,
             slips: List<Slip>,
             total_results: String
        ):T

        class ToDomainMapper @Inject constructor(
            private val mapper: Slip.Mapper<String>
        ): Mapper<AdvicesDomain>{
            @SuppressLint("SimpleDateFormat")
            override fun map(
                query: String,
                slips: List<Slip>,
                total_results: String,
            ): AdvicesDomain {
                var advices =""
                slips.forEach {advices+="- ${it.map(mapper)}\n"}
                return AdvicesDomain(
                    advices = advices,
                    date = SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().time),
                    searchTerm = query
                )
            }

        }
    }

}