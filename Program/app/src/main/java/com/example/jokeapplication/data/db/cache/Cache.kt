package com.example.jokeapplication.data.db.cache

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "cache")
data class Cache(
    @SerialName("id")
    @PrimaryKey
    val id: String,
    @SerialName("category")
    val category: String,
    @SerialName("question")
    val question: String,
    @SerialName("answer")
    val answer: String,
    val fromNetwork: Boolean,
    val timestamp: Long
)
