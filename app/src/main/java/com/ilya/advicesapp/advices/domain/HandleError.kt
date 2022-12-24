package com.ilya.advicesapp.advices.domain


import com.ilya.advicesapp.R
import javax.inject.Inject

interface HandleError<T> {

    fun handle(e: Exception):T

    class Base @Inject constructor(
        private val managerResource: ManagerResource
    ): HandleError<String>{
        override fun handle(e: Exception): String = managerResource.getString(
            when(e){
                is NoInternetConnectionException -> R.string.no_connection_message
                is NoSuchElementException -> R.string.no_element
                else -> R.string.service_is_unavailable
            }
        )

    }
}
