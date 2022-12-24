package com.ilya.advicesapp.advices.data.cloud

import com.ilya.advicesapp.advices.domain.AdvicesDomain
import retrofit2.Response
import java.net.UnknownServiceException
import javax.inject.Inject

/**
 * Created by HP on 07.12.2022.
 **/
interface HandleCloudResponse {

    suspend fun handle(block:suspend ()->Response<AdviceCloud>): AdvicesDomain

    class Base @Inject constructor(
        private val mapper: AdviceCloud.Mapper<AdvicesDomain>
    ): HandleCloudResponse {

        override suspend fun handle(block: suspend () -> Response<AdviceCloud>): AdvicesDomain {
            val result = block.invoke()
            if(result.isSuccessful){
                if(result.body()!=null){
                    if (result.body()?.total_results==null)
                        throw NoSuchElementException()
                    else {
                        return result.body()?.map(mapper)?:AdvicesDomain(advices = "", date = "", searchTerm = "")
                    }
                }else return AdvicesDomain(advices = "", date = "", searchTerm = "")
            }else throw UnknownServiceException()
        }

    }
}