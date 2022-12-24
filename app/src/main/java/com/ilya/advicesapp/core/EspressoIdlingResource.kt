package com.ilya.advicesapp.core

import androidx.test.espresso.idling.CountingIdlingResource

/**
 * Created by HP on 24.12.2022.
 **/
object EspressoIdlingResource {

    private const val RESOURSE = "GLOBAL"

    @JvmStatic
    val countingIdlingResource = CountingIdlingResource(RESOURSE)

    fun increment() = countingIdlingResource.increment()


    fun decrement(){
        if(!countingIdlingResource.isIdleNow)
            countingIdlingResource.decrement()
    }
}