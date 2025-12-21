package com.example.jokeapplication.ui.joke_list

//import com.example.jokeapplication.data.db.cache.CacheDatabase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jokeapplication.data.Joke
import com.example.jokeapplication.data.JokeManager
import com.example.jokeapplication.data.JokesRepository
import com.example.jokeapplication.data.db.JokeDatabase
import com.example.jokeapplication.data.db.cache.CacheDatabase
import com.example.jokeapplication.data.network.JokeApiState
import com.example.jokeapplication.data.network.RetrofitInstance
import com.example.jokeapplication.data.network.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class JokeViewModel : ViewModel() {

    private val manager = JokeManager()

    private val repository: JokesRepository by lazy {
        JokesRepository(
            RetrofitInstance.api,
            JokeDatabase.INSTANCE.jokeDao(),
            CacheDatabase.INSTANCE.cacheDao()
        )
    }

    val commentState = MutableStateFlow(
        JokeApiState(Status.LOADING)
    )

    init {
        fetchNetworkJokes()
    }

    fun fetchNetworkJokes() {
        commentState.value = JokeApiState.loading()
        viewModelScope.launch {
            repository.getJokes()
                .collect {
                    try {
                        val comment = RetrofitInstance.api.getRandomJoke()
                        JokesRepository(RetrofitInstance.api, JokeDatabase.INSTANCE.jokeDao(), CacheDatabase.INSTANCE.cacheDao()).getCache()
                        val listJokes = comment.jokes
                        for (num in listJokes!!) {
                            manager.generator.addNewJokeEnd(
                                num.category,
                                num.setup,
                                num.delivery,
                                true
                            )
                        }
                    } catch(e: Exception) {
                        commentState.value = JokeApiState.error()
                    }
                }
            commentState.value = JokeApiState.success()
        }
    }

    fun addJoke(joke: Joke) {
        commentState.value = JokeApiState.loading()
        viewModelScope.launch {
            repository.addJoke(joke)
            JokeManager().generator.addNewJokeStart(joke)
            commentState.value = JokeApiState.success()
        }
    }

    fun loadAllJokes() {
        viewModelScope.launch {
            repository.getAllJokes().collect {
                for (joke in it) {
                    JokeManager().generator.addNewJokeStart(joke)
                }
            }
        }
    }
}