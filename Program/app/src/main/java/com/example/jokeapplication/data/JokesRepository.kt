package com.example.jokeapplication.data

import com.example.jokeapplication.data.db.JokeDao
import com.example.jokeapplication.data.db.cache.Cache
import com.example.jokeapplication.data.db.cache.CacheDao
import com.example.jokeapplication.data.network.JokeApiState
import com.example.jokeapplication.data.network.JokesApi
import com.example.jokeapplication.data.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.util.UUID

class JokesRepository(private val apiService: JokesApi, private val jokeDao: JokeDao, private val cacheDao: CacheDao) {

    private val CACHE_TIMEOUT = 24 * 60 * 60 * 1000L

    suspend fun getCache(): List<Cache> {
        return withContext(Dispatchers.IO) {
            val cachedJokes = cacheDao.getCachedJokes(System.currentTimeMillis()- CACHE_TIMEOUT).firstOrNull()
            (if (!cachedJokes.isNullOrEmpty()) {
                cachedJokes
            } else {
                try {
                    val jokesDto = RetrofitInstance.api.getRandomJoke().jokes
                    val jokes = jokesDto!!.map {
                        Cache(
                            id = UUID.randomUUID().toString(),
                            category = it.category,
                            question = it.setup,
                            answer = it.delivery,
                            fromNetwork = true,
                            timestamp = System.currentTimeMillis()
                        )
                    }
                    cacheDao.clearCache()
                    cacheDao.insertCache(jokes)
                    emptyList()
                } catch (e: Exception) {
                    emptyList()
                }
            }) as List<Cache>
        }
    }

    suspend fun getJokes(): Flow<JokeApiState> {
        return flow {
            emit(JokeApiState.success())
        }.flowOn(Dispatchers.IO)
    }

    fun getAllJokes(): Flow<List<Joke>> = jokeDao.getAllJokes()

    suspend fun addJoke(joke: Joke) {
        jokeDao.insert(joke)
    }
}