package com.ilya.advicesapp.advices.data.cache

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * Created by HP on 26.11.2022.
 **/
@Dao
interface AdviceDao {

    @Query("SELECT * FROM advices ORDER BY time DESC")
     fun readAllHistory(): Flow<List<AdvicesCache>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun saveAdviceItem(item: AdvicesCache)

    @Query("SELECT * FROM advices WHERE searchTerm = :word")
     suspend fun historyItem(word: String): AdvicesCache?
}