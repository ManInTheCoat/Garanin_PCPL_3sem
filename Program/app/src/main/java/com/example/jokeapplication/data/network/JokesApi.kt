package com.example.jokeapplication.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface JokesApi {
    @GET("/joke/Any")
    suspend fun getRandomJoke(
        @Query("blacklistFlags") blacklistFlags: List<String> = listOf(
            "nsfw",
            "religious",
            "political",
            "racist",
            "sexist",
            "explicit"
        ),
        @Query("type") type: String = "twopart",
        @Query("amount") amount: Int = 10
    ): JokesResponse
}