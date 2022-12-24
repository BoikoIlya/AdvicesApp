package com.ilya.advicesapp.advices.data.cache

import com.ilya.advicesapp.advices.domain.AdvicesDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by HP on 05.12.2022.
 **/
class CacheDataSourceTest {

    lateinit var dao: AdviceDaoTest
    lateinit var cacheDataSource: CacheDataSource

    @Before
    fun setup(){

        dao = AdviceDaoTest()
        cacheDataSource = CacheDataSource.Base(dao,TestMapper(dao.date))
    }

    @Test
    fun `test read all advices empty`() = runBlocking {
       val result = cacheDataSource.readAdvices().first()
        assertEquals(result, dao.list.toList())
    }

    @Test
    fun `test read all advice not empty`() = runBlocking {
        val expected = listOf(
            AdvicesDomain(advices = "aaa", date = "12", searchTerm = "a"),
            AdvicesDomain(advices = "aaa", date = "12", searchTerm = "a"))

        dao.list.add(AdvicesCache(advices = "aaa", date = "12", searchTerm = "a", time = 0))
        dao.list.add(AdvicesCache(advices = "aaa", date = "12", searchTerm = "a", time = 0))

        val actual = cacheDataSource.readAdvices().first()
        assertEquals(expected, actual)
    }

    @Test
    fun `test save`(){
        val expected =  AdvicesCache(
            advices = "aaa", date = "12", searchTerm = "a", time = 0
        )

        cacheDataSource.saveAdvices(
            AdvicesDomain(advices = "aaa", date = "12", searchTerm = "a"))

        val actual = dao.list[0]

        assertEquals(expected, actual)
    }

    @Test
    fun `test contains empty`()= runBlocking{
        val expected = false
        val actual = cacheDataSource.containsWord("js")

        assertEquals(expected, actual)
    }

    @Test
    fun `test contains not empty`()= runBlocking{
        val expected = true

        dao.list.add(AdvicesCache(advices = "aaa", date = "12", searchTerm = "a", time = 0))

        val actual = cacheDataSource.containsWord("a")

        assertEquals(expected, actual)
    }

    @Test
    fun `test advice doesnt exist`() = runBlocking{
        val expected = NoSuchElementException()

        val actual = runCatching {
            cacheDataSource.advices("b")
        }.onFailure { actual->
            assertEquals(expected::class, actual::class)
        }

        assertNotEquals(expected, actual)
    }

    @Test
    fun `test advice exist`() = runBlocking{
        val expected = AdvicesDomain(advices = "aaa", date = "12", searchTerm = "a")

        dao.list.add(AdvicesCache(advices = "aaa", date = "12", searchTerm = "a", time = 0))

        val actual = cacheDataSource.advices("a")

        assertEquals(expected, actual)
    }


    class AdviceDaoTest: AdviceDao {
        val list = mutableListOf<AdvicesCache>()
        val date = 5L

        override  fun readAllHistory(): Flow<List<AdvicesCache>> {
            return flow{ emit(list) }
        }

        override fun saveAdviceItem(item: AdvicesCache) {
            list.add(item)
        }

        override suspend fun historyItem(word: String): AdvicesCache? {
            return list.find { it.searchTerm==word }
        }

    }

   class TestMapper(val date: Long): AdvicesDomain.Mapper<AdvicesCache>{
       override fun map(extension: String, language_name: String, program: String): AdvicesCache {
           return AdvicesCache(extension, language_name, program, time = 0)
       }

   }

}