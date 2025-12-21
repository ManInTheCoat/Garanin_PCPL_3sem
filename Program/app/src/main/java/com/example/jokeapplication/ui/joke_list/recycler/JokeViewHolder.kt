package com.example.jokeapplication.ui.joke_list.recycler

import androidx.recyclerview.widget.RecyclerView
import com.example.jokeapplication.data.Joke
import com.example.jokeapplication.databinding.JokeItemBinding

class JokeViewHolder(
    private val binding: JokeItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(joke: Joke) {
        bindCategory(joke.category)
        bindQuestion(joke.question)
        bindAnswer(joke.answer)
        bindFromNetwork(joke.fromNetwork)
    }

    fun bindCategory(category: String) {
        binding.category.text = category
    }

    fun bindQuestion(question: String) {
        binding.question.text = question
    }

    fun bindAnswer(answer: String) {
        binding.answer.text = answer
    }

    fun bindFromNetwork(fromNetwork: Boolean) {
        if (fromNetwork) {
            binding.fromNetwork.text = "Из сети"
        } else {
            binding.fromNetwork.text = "С устройства"
        }
    }

    fun getID(joke: Joke): String {
        return joke.id
    }
}