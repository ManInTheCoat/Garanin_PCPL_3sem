package com.example.jokeapplication.data.db.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CacheDao {
    @Insert
    suspend fun insertCache(jokes: List<Cache>)

    @Query("SELECT * FROM cache WHERE timestamp > :timestamp")
    fun getCachedJokes(timestamp: Long): Flow<List<Cache>>

    @Query("DELETE FROM cache")
    suspend fun clearCache()
}