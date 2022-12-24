package com.ilya.advicesapp.advices.data.cloud

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by HP on 26.11.2022.
 **/
interface AdviceService {

    @GET("/advice/search/{query}")
    suspend fun findAdvices(
        @Path("query")
        word: String
    ): Response<AdviceCloud>

    @GET("/advice")
    suspend fun randomAdvice(): Response<SlipRandom>
}