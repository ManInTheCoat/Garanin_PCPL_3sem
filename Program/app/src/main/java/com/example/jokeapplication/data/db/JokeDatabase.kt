package com.example.jokeapplication.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jokeapplication.data.Joke

@Database(entities = [Joke::class], version = 1)
abstract class JokeDatabase: RoomDatabase() {
    abstract fun jokeDao(): JokeDao

    companion object {
        @Volatile
        lateinit var INSTANCE: JokeDatabase

        fun initDatabase(context: Context) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                JokeDatabase::class.java,
                "joke_database"
            ).build()
            INSTANCE = instance
        }
    }
}