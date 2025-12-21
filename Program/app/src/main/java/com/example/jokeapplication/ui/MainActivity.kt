package com.example.jokeapplication.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.jokeapplication.R
import com.example.jokeapplication.databinding.ActivityMainBinding
import com.example.jokeapplication.ui.joke_list.JokeListFragment
import com.example.jokeapplication.ui.joke_list.JokeViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            JokeViewModel().loadAllJokes()
            openFragment()
        }
    }

    private fun openFragment() {
        val fragment = JokeListFragment()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, fragment)
            .commit()
    }
}