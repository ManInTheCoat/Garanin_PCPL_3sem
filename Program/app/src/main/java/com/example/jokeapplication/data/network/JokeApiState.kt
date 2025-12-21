package com.example.jokeapplication.data.network

data class JokeApiState(val status: Status) {

    companion object {

        fun success(): JokeApiState {
            return JokeApiState(Status.SUCCESS)
        }

        fun error(): JokeApiState {
            return JokeApiState(Status.ERROR)
        }

        fun loading(): JokeApiState {
            return JokeApiState(Status.LOADING)
        }
    }
}
