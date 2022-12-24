package com.ilya.advicesapp.uitest

import androidx.test.espresso.Espresso
import org.junit.Test

/**
 * Created by HP on 24.12.2022.
 **/
class NavigationTest: BaseTest() {

    @Test
    fun detailsNavigation(){
        val advicesPage = AdvicesPage()

        advicesPage.run {
            input.typeText("Once")
            findAdviceBtn.click()

            recycler.run {
                viewInRecycler(0,titleItem).checkText("Once")
                viewInRecycler(0,titleItem).click()
            }
        }

        DetailsPage().run {
            tittle.checkText("Once")
            content.checkText("- Once in a while, eat some sweets " +
                    "you used to enjoy when you were younger.")
        }

        Espresso.pressBack()
        advicesPage.run {
            recycler.run {
                viewInRecycler(0, titleItem).checkText("Once")
            }
        }
    }

}