package com.ilya.advicesapp.advices.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by HP on 05.12.2022.
 **/
@Database(entities = [AdvicesCache::class], version = 1)
abstract class AdviceDataBase: RoomDatabase() {

    abstract fun getDao(): AdviceDao
}