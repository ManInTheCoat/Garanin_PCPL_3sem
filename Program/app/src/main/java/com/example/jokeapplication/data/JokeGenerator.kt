package com.example.jokeapplication.data

import com.example.jokeapplication.ui.joke_list.JokeViewModel
import java.util.UUID

class JokeGenerator {

    private val dataBase = JokeStorage

    fun addNewJokeStart(joke: Joke) {
        dataBase.data.add(0, joke)
    }

    fun addNewJokeDB(category: String, question: String, answer: String, fromNetwork: Boolean) {
        JokeViewModel().addJoke(
            Joke(
                id = UUID.randomUUID().toString(),
                category = category,
                question = question,
                answer = answer,
                fromNetwork = fromNetwork
            )
        )
    }

    fun addNewJokeEnd(category: String, question: String, answer: String, fromNetwork: Boolean) {
        dataBase.data.add(
            Joke(
                id = UUID.randomUUID().toString(),
                category = category,
                question = question,
                answer = answer,
                fromNetwork = fromNetwork
            )
        )
    }

}