package com.ilya.advicesapp.advices.data

interface FetchLanguage<T> {

    suspend fun advices(name:String): T
}
