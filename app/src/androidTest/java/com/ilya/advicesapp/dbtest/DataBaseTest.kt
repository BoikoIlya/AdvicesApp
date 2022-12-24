package com.ilya.advicesapp.dbtest

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.ilya.advicesapp.advices.data.cache.AdvicesCache
import com.ilya.advicesapp.advices.data.cache.AdviceDao
import com.ilya.advicesapp.advices.data.cache.AdviceDataBase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by HP on 05.12.2022.
 **/
class DataBaseTest {

    lateinit var db: AdviceDataBase
    lateinit var dao: AdviceDao

    @Before
    fun setup(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AdviceDataBase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.getDao()
    }

    @After
    fun tearDown(){
        db.close()
    }

    @Test
    fun test_save_and_test_historyItem()= runBlocking{
        val expected = AdvicesCache(advices = "ts", date = "1", searchTerm = "ts", time = 0)
        dao.saveAdviceItem(AdvicesCache(advices = "ts", date = "1", searchTerm = "ts", time = 0))
        assertEquals(expected ,dao.historyItem("ts"))
    }

    @Test
    fun test_readAllHistory()= runBlocking{
        val list = listOf(
            AdvicesCache(advices = "ts", date = "1", searchTerm = "ts", time = 0),
            AdvicesCache(advices = "js", date = "2", searchTerm = "js", time = 1)
        )
        list.forEach {
            dao.saveAdviceItem(it)
        }

       val expected =  list.sortedByDescending { it.time }

        assertEquals(expected, dao.readAllHistory().first())
    }
}