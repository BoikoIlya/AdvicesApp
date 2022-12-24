package com.ilya.advicesapp.uitest

import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ilya.advicesapp.core.EspressoIdlingResource
import com.ilya.advicesapp.main.presentation.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith


/**
 * Created by HP on 20.12.2022.
 **/
@RunWith(AndroidJUnit4::class)
abstract class BaseTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup(){
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    protected fun ViewInteraction.typeText(value: String) {
        perform(ViewActions.clearText())
        perform(ViewActions.typeText(value))
        Espresso.closeSoftKeyboard()
    }

    protected fun ViewInteraction.checkText(value: String) {
        check(ViewAssertions.matches(ViewMatchers.withText(value)))
    }

    protected fun ViewInteraction.click() {
        perform(ViewActions.click())
    }

    protected fun Int.viewInRecycler(position: Int, viewId: Int): ViewInteraction =
        Espresso.onView(RecyclerViewMatcher(this).atPosition(position, viewId))
}