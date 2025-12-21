package com.example.jokeapplication.ui.joke_generate

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.jokeapplication.R
import com.example.jokeapplication.data.JokeManager
import com.example.jokeapplication.databinding.FragmentJokeGenerateBinding

class JokeGenerateFragment : Fragment(R.layout.fragment_joke_generate) {

    private val binding by viewBinding(FragmentJokeGenerateBinding::bind)

    private val generator = JokeManager().generator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {
            addNewJoke()
        }
    }

    private fun addNewJoke() {
        val textCategory = binding.editCategory.text.toString()
        val textQuestion = binding.editQuestion.text.toString()
        val textAnswer = binding.editAnswer.text.toString()
        if (textCategory == "" || textQuestion == "" || textAnswer == "") {
            binding.textError.text = "Нужно заполнить все пустые поля!"
        } else {
            generator.addNewJokeDB(textCategory, textQuestion, textAnswer, false)
            parentFragmentManager.popBackStack()
        }
    }

}