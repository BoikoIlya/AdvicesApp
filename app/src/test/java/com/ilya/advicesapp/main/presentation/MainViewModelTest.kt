package com.ilya.advicesapp.main.presentation

import com.ilya.advicesapp.workmanager.WorkManagerWrapper
import com.ilya.advicesapp.workmanager.di.WorkManagerModule
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by HP on 15.11.2022.
 **/
class MainViewModelTest: BaseTest(){

    @Test
    fun `test launching work manager`(){
        val workManagerWrapper =  WorkManagerWrapperTest()
        val viewModel = MainViewModel(
            navigationCommunication =TestNavigationCommunication(),
            workManagerWrapper = workManagerWrapper
        )

        assertEquals(1, workManagerWrapper.callCounter)
    }

    class WorkManagerWrapperTest: WorkManagerWrapper{
        var callCounter =0

        override fun start() {
            callCounter++
        }

    }
}

