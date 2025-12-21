package com.example.jokeapplication.data.db.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Cache::class], version = 1)
abstract class CacheDatabase: RoomDatabase() {
    abstract fun cacheDao(): CacheDao

    companion object {
        @Volatile
        lateinit var INSTANCE: CacheDatabase

        fun initDatabase(context: Context) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                CacheDatabase::class.java,
                "cache_database"
            ).build()
            INSTANCE = instance
        }
    }
}