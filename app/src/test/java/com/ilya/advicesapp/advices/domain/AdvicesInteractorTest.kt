package com.ilya.advicesapp.advices.domain

import app.cash.turbine.test
import com.ilya.advicesapp.advicedetails.data.AdviceDetails
import com.ilya.advicesapp.advices.data.AdviceRepository
import com.ilya.advicesapp.advices.presentation.AdviceResult
import com.ilya.advicesapp.advices.presentation.AdvicesUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by HP on 25.11.2022.
 **/
class AdvicesInteractorTest {

    lateinit var interactor: AdviceInteractor
    lateinit var repository: TestAdviceRepository
    lateinit var managerResource: TestManagerResource

    @Before
    fun setup(){

        repository = TestAdviceRepository()
        managerResource = TestManagerResource()
        interactor = AdviceInteractor.Base(
          adviceDetails = AdviceDetails.Base(AdvicesUi(advices = "aaa", date = "12", searchTerm = "a")),
          repository =  repository,
            handleRequest = HandleRequest.Base(HandleError.Base(managerResource),repository)
        )
    }


    @Test
    fun `test init`()= runBlocking{
        interactor.init().test {
            assertEquals(AdviceResult.Loading, awaitItem())
            assertEquals(AdviceResult.Success(repository.item.value), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `test find language success`() = runBlocking {
        interactor.findAdvices("b").test {
            assertEquals(AdviceResult.Loading, awaitItem())
            assertEquals(AdviceResult.Success(repository.item.value), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `test find language error`() = runBlocking {
        repository.expectError = true
        interactor.findAdvices("").test {
            assertEquals(AdviceResult.Loading, awaitItem())
            assertEquals(AdviceResult.Error(""), awaitItem())
            awaitComplete()
        }
    }

@Test
fun `test random advice`()= runBlocking{
    interactor.randomAdvice().test {
        assertEquals(AdviceResult.Loading, awaitItem())
        assertEquals(AdviceResult.Success(repository.randomAdvice), awaitItem())
        awaitComplete()
    }
}

    class TestManagerResource: ManagerResource{
        private var value = ""

        fun changeExpectedValue(value: String) {
            this.value = value
        }

        override fun getString(id: Int): String = value

    }

    class TestAdviceRepository: AdviceRepository {
        var expectError = false
        val item = MutableStateFlow(listOf(AdvicesDomain(advices = "aaa", date = "12", searchTerm = "a")))
        private val listToFind = listOf(AdvicesDomain(advices = "bbb", date = "bb", searchTerm = "b"))
        private lateinit var mapperMaches: AdvicesDomain.Mapper<Boolean>
        var randomAdvice = listOf(AdvicesDomain(advices = "ccc", date = "123", searchTerm = "c"))

        override suspend fun allAdvices(): Flow<List<AdvicesDomain>> {
           return item
        }

        override suspend fun findAdvices(name: String){
           mapperMaches = AdvicesDomain.Mapper.Matches(name)
            if(listToFind.first().map(mapperMaches))
            item.value = listToFind
            else throw NoSuchElementException()
        }

        override suspend fun randomAdvice() {
            item.value = randomAdvice
        }

    }



}