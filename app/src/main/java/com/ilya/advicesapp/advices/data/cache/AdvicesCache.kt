package com.ilya.advicesapp.advices.data.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by HP on 26.11.2022.
 **/
@Entity(tableName = "advices")
data class AdvicesCache(
     val advices: String,
     val date: String,
     @PrimaryKey(autoGenerate = false)
     val searchTerm: String,
     val time: Long
)