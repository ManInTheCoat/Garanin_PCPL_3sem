package com.example.jokeapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "jokes")
data class Joke(
    @SerialName("id")
    @PrimaryKey
    val id: String,
    @SerialName("category")
    val category: String,
    @SerialName("question")
    val question: String,
    @SerialName("answer")
    val answer: String,
    val fromNetwork: Boolean

)
