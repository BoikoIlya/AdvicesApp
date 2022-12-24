package com.ilya.advicesapp.advices.data

import app.cash.turbine.test
import com.ilya.advicesapp.advices.data.cache.CacheDataSource
import com.ilya.advicesapp.advices.data.cloud.CloudDataSource
import com.ilya.advicesapp.advices.data.cloud.CloudDataSourceTest
import com.ilya.advicesapp.advices.domain.AdvicesDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import java.lang.IllegalStateException
import java.util.ArrayList

/**
 * Created by HP on 26.11.2022.
 **/
class AdviceRepositoryTest {

    lateinit var repository: AdviceRepository
    lateinit var cache: TestCacheDataSource
    lateinit var cloud: TestCloudDataSource


    @Before
    fun setup(){
        cache = TestCacheDataSource()
        cloud = TestCloudDataSource()

        repository = AdviceRepository.Base(
            cache, cloud,HandleDataRequest.Base(HandleDomainError(),cache)
        )
    }

    @Test
    fun `test init`()= runBlocking{
        val expected = listOf(AdvicesDomain(advices = "a", date = "1", searchTerm = "a"))
        cache.replaceData(expected)
        repository.allAdvices().test {
            assertEquals(
                expected,
                awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `test find cache`()= runBlocking {
        val expected = listOf(AdvicesDomain(advices = "a", date = "1", searchTerm = "a"))
        cache.replaceData(expected)
        repository.findAdvices("a")
        repository.allAdvices().test {

            assertEquals(
                expected,
                awaitItem())
            awaitComplete()
        }
    }

        @Test
        fun `test find cloud success`() = runBlocking{
            val expected = listOf(AdvicesDomain(advices = "a", date = "1", searchTerm = "a"))
            cloud.replaceData(expected)
            repository.allAdvices().test {
                repository.findAdvices("a")
                assertEquals(
                    expected,
                    awaitItem()
                )
                awaitComplete()
            }
        }

        @Test(expected = NoSuchElementException::class)
        fun `test find cloud error`() = runBlocking {
            val expected: Throwable = NoSuchElementException()
            cloud.setException(expected)
            repository.allAdvices().collect{
                repository.findAdvices("")
            }

        }

    @Test
    fun `test random advice`() = runBlocking{
        val expected = listOf(AdvicesDomain(advices = "a", date = "1", searchTerm = "a"))
        cloud.replaceData(expected)
        repository.randomAdvice()
        repository.allAdvices().test {
            assertEquals(
                expected,
                awaitItem()
            )
            awaitComplete()
        }
    }

        class TestCloudDataSource : CloudDataSource {
           private val cloudList = mutableListOf<AdvicesDomain>()
           private var exception:Throwable = Exception()

            fun replaceData(data: List<AdvicesDomain>){
                cloudList.clear()
                cloudList.addAll(data)
            }

            fun setException(e: Throwable) {
                this.exception = e
            }

            override suspend fun randomAdvice(): AdvicesDomain {
                return cloudList.first()
            }

            override suspend fun advices(name: String): AdvicesDomain {
                return cloudList.find { it.map(AdvicesDomain.Mapper.Matches(name)) }
                    ?: throw exception
            }

        }

        class TestCacheDataSource : CacheDataSource {
           private val list = mutableListOf<AdvicesDomain>()

            fun replaceData(data: List<AdvicesDomain>){
                list.clear()
                list.addAll(data)
            }


            override suspend fun readAdvices(): Flow<List<AdvicesDomain>> {
                return flow {
                    emit(list)
                }
            }

            override suspend fun containsWord(name: String): Boolean {
                return list.find { it.map(AdvicesDomain.Mapper.Matches(name)) } != null
            }

            override fun saveAdvices(item: AdvicesDomain) {
                if(!list.contains(item))
                    list.add(item)
            }


            override suspend fun advices(name: String): AdvicesDomain {
               return list.first()
            }

        }

}

