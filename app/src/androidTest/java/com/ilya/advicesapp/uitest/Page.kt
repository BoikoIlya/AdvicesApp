package com.ilya.advicesapp.uitest

import androidx.test.espresso.Espresso
import androidx.test.espresso.matcher.ViewMatchers

/**
 * Created by HP on 20.12.2022.
 **/
abstract class Page {

    protected fun Int.view() = Espresso.onView(ViewMatchers.withId(this))

}