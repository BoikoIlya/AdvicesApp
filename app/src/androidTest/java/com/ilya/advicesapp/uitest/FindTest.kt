package com.ilya.advicesapp.uitest

import org.junit.Test

/**
 * Created by HP on 20.12.2022.
 **/
class FindTest: BaseTest() {



    @Test
    fun test_history(): Unit = AdvicesPage().run {
        input.typeText("car")
        findAdviceBtn.click()
        recycler.run {
            viewInRecycler(0,titleItem).checkText("car")
        }

        input.typeText("if")
        findAdviceBtn.click()
        recycler.run {
            viewInRecycler(0,titleItem).checkText("if")
            viewInRecycler(1,titleItem).checkText("car")
        }

        input.typeText("car")
        findAdviceBtn.click()
        recycler.run {
            viewInRecycler(0, titleItem).checkText("car")
            viewInRecycler(1, titleItem).checkText("if")
        }
    }

}