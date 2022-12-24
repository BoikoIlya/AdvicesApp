package com.ilya.advicesapp.advices.presentation

import android.view.View
import com.ilya.advicesapp.advices.data.cloud.Slip
import com.ilya.advicesapp.advices.domain.AdviceInteractor
import com.ilya.advicesapp.advices.domain.AdvicesDomain
import com.ilya.advicesapp.advices.domain.LanguageUiMapper
import com.ilya.advicesapp.main.presentation.BaseTest
import com.ilya.advicesapp.main.presentation.NavigationStrategy
import com.ilya.advicesapp.main.presentation.Screen
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by HP on 15.11.2022.
 **/
class AdvicesViewModelTest: BaseTest() {

    lateinit var navigation: TestNavigationCommunication
    lateinit var viewModel: AdvicesViewModel
    lateinit var communication: TestLanguageCommunication
    lateinit var interactor: TestAdviceInteractor


    @ExperimentalCoroutinesApi
    @Before
    fun setup(){
        navigation = TestNavigationCommunication()
        communication = TestLanguageCommunication()
        interactor = TestAdviceInteractor()
        val testDispatchersList = TestDispatchersList()
        val handleRequest = HandleAdviceResult.Base(
            communication,
            TestDispatchersList(),
            AdviceResultMapper(communication,LanguageUiMapper()))
        viewModel = AdvicesViewModel.Base(
            handleRequest,
            interactor,
            communication,
            navigation,
            testDispatchersList
        )

    }

    @Test
    fun `test init, find, random`() = runBlocking{
        assertEquals(View.VISIBLE, communication.progressList[0])
        assertEquals(View.GONE, communication.progressList[1])
        assertEquals(2, communication.progressList.size)
        assertEquals(1, communication.stateList.size)
        assertEquals(0, communication.languageList.size)

        interactor.changeExpectedResult(AdviceResult.Error(""))
        viewModel.findAdvices("s")

        assertEquals(View.VISIBLE, communication.progressList[2])
        assertEquals(View.GONE, communication.progressList[3])
        assertEquals(4, communication.progressList.size)
        assertEquals(2, communication.stateList.size)
        assertEquals(0, communication.languageList.size)

        val expected = listOf(AdvicesUi(advices = "1", date = "1", searchTerm = "1"))
        interactor.changeExpectedResult(AdviceResult.Success(listOf(
            AdvicesDomain(advices = "1",
                date = "1",
                searchTerm = "1"))))
        viewModel.randomAdvice()
        assertEquals(View.VISIBLE, communication.progressList[4])
        assertEquals(View.GONE, communication.progressList[5])
        assertEquals(6, communication.progressList.size)
        assertEquals(3, communication.stateList.size)
        assertEquals(1, communication.languageList.size)
        assertEquals(expected, communication.languageList)
    }


    @Test
    fun `test show details navigation`(){
        val item = AdvicesUi(
            "aaa",
            "12.12.12",
            "bbb"
        )
        viewModel.showDetails(item)
        assertEquals(item, interactor.details)
        assertEquals(NavigationStrategy.Add(Screen.Details), navigation.strategy)
    }

    @Test
    fun `test clear error`(){
        viewModel.clearError()
        assertEquals(2,communication.stateList.size)
        assertEquals(true,communication.stateList[1] is UiState.ClearError)
    }



    class TestAdviceInteractor: AdviceInteractor{
        private var result:AdviceResult = AdviceResult.Success()
         var details: AdvicesUi = AdvicesUi(advices = "", date = "", searchTerm = "")

        override fun saveDetails(item: AdvicesUi) {
            details = item
        }

        override suspend fun randomAdvice(): Flow<AdviceResult> {
            return flow{
                emit(AdviceResult.Loading)
                emit(result)
            }
        }


        override suspend fun init(): Flow<AdviceResult> = flow {
            emit(AdviceResult.Loading)
            emit(result)
        }

        override suspend fun findAdvices(name: String): Flow<AdviceResult> =
            flow {
            emit(AdviceResult.Loading)
            emit(result)
        }

        fun changeExpectedResult(newResult: AdviceResult) {
            result = newResult
        }

    }

    @ExperimentalCoroutinesApi
    private class TestDispatchersList(
        private val dispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()
    ) : DispatchersList {

        override fun io(): CoroutineDispatcher = dispatcher
        override fun ui(): CoroutineDispatcher = dispatcher
    }

}