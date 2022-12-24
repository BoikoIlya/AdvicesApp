package com.ilya.advicesapp.advicedetails.presentation

import com.ilya.advicesapp.advicedetails.data.AdviceDetails
import com.ilya.advicesapp.advices.presentation.AdvicesUi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by HP on 17.12.2022.
 **/
class DetailsViewModelTest {

    lateinit var detailsViewModel: DetailsViewModel
    lateinit var details: DetailsTest

    @Before
    fun setup(){
         details = DetailsTest()
        detailsViewModel = DetailsViewModel(details)
    }

    @Test
    fun `test read`() {
        val expected = AdvicesUi(advices = "1", date = "1", searchTerm = "1")
        details.save(expected)

        val actual = detailsViewModel.read()

        assertEquals(expected, actual)
    }

    class DetailsTest:AdviceDetails.Mutable<AdvicesUi>{
            private var data = AdvicesUi(advices = "", date = "", searchTerm = "")

        override fun read(): AdvicesUi = data

        override fun save(data: AdvicesUi) {
            this.data = data
        }

    }
}