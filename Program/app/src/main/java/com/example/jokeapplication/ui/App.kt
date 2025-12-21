package com.example.jokeapplication.ui

import android.app.Application
import com.example.jokeapplication.data.db.JokeDatabase
import com.example.jokeapplication.data.db.cache.CacheDatabase

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        JokeDatabase.initDatabase(this)
        CacheDatabase.initDatabase(this)
    }
}