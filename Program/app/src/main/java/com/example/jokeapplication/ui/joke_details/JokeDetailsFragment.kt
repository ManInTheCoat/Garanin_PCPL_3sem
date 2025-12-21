package com.example.jokeapplication.ui.joke_details

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.jokeapplication.R
import com.example.jokeapplication.data.Joke
import com.example.jokeapplication.data.JokeManager
import com.example.jokeapplication.databinding.FragmentJokeDetailsBinding

class JokeDetailsFragment : Fragment(R.layout.fragment_joke_details) {

    private val binding by viewBinding(FragmentJokeDetailsBinding::bind)

    private val dataBase = JokeManager().data

    private var jokeID: String? = null

    companion object {

        private const val ARG_JOKEID = "jokeID"

        fun newInstance(jokeID: String) =
            JokeDetailsFragment().apply {
                arguments = bundleOf(ARG_JOKEID to jokeID)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            jokeID = it.getString(ARG_JOKEID)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleExtra()
    }

    private fun handleExtra() {
        if (jokeID == null) {
            handleError()
        } else {
            val item = jokeID?.let { findJokeByID(it) }

            if (item != null) {
                setupJokeData(item)
            } else {
                handleError()
            }
        }
    }

    private fun findJokeByID(jokeID: String): Joke? {
        for (joke in dataBase) {
            if (joke.id == jokeID) {
                return joke
            }
        }
        handleError()
        return null
    }

    private fun setupJokeData(joke: Joke) {
        with(binding) {
            category.text = "Категория: ${joke.category}"
            question.text = "Вопрос: ${joke.question}"
            answer.text = "Ответ: ${joke.answer}"
            if (joke.fromNetwork) {
                fromNetwork.text = "Из сети"
            } else {
                fromNetwork.text = "С устройства"
            }
        }
    }

    private fun handleError() {
        Toast.makeText(activity, "Invalid joke data", Toast.LENGTH_SHORT).show()
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.remove(this)
            ?.commit()
    }
}