package com.ilya.advicesapp.core

/**
 * Created by HP on 07.12.2022.
 **/
interface Mapper<T, R> {

    fun map(data: T): R

    interface Unit<T>:Mapper<T, kotlin.Unit>{
       override fun map(data: T)
    }
}