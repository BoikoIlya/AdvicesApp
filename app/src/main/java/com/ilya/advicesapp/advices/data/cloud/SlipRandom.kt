package com.ilya.advicesapp.advices.data.cloud

import android.annotation.SuppressLint
import com.ilya.advicesapp.advices.domain.AdvicesDomain
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

data class SlipRandom(
   private val slip: SlipX
){

    fun <T>map(mapper: Mapper<T>) = mapper.map(slip)

    interface Mapper<T>{
        fun map(
            slip: SlipX
        ): T


        class ToAdviceDomainMapper @Inject constructor(): Mapper<AdvicesDomain> {
        @SuppressLint("SimpleDateFormat")
        override fun map(slip: SlipX): AdvicesDomain {
            val str =slip.advice.split(Regex(" "))
            return AdvicesDomain(
                advices = "- ${slip.advice}",
                date = SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().time),
                searchTerm = str[0] ?: (" " + " " + str[1]) ?: " "
            )
        }

    }}
}