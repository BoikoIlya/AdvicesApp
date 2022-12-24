package com.ilya.advicesapp.advices.data

import com.ilya.advicesapp.core.ServiceUnavailableException
import com.ilya.advicesapp.advices.domain.HandleError
import com.ilya.advicesapp.advices.domain.NoInternetConnectionException
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * Created by HP on 07.12.2022.
 **/
class HandleDomainError @Inject constructor() : HandleError<Exception> {

    override fun handle(e: Exception) = when (e) {
        is UnknownHostException -> NoInternetConnectionException()
        is NoSuchElementException -> e
        else -> ServiceUnavailableException()
    }

}