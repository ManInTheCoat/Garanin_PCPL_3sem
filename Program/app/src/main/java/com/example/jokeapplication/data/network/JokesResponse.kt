package com.example.jokeapplication.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JokesResponse(
    @SerialName("jokes")
    val jokes: List<JokeResponse>?=null
)