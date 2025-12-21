package com.example.jokeapplication.data

class JokeManager {

    val data = JokeStorage.data

    val generator = JokeGenerator()

    fun isDataNull(): Boolean {
        return data.size == 0
    }

}