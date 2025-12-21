package com.example.jokeapplication.ui.joke_list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.jokeapplication.R
import com.example.jokeapplication.data.JokeManager
import com.example.jokeapplication.data.JokesRepository
import com.example.jokeapplication.data.db.JokeDatabase
import com.example.jokeapplication.data.db.cache.CacheDatabase
import com.example.jokeapplication.data.network.RetrofitInstance
import com.example.jokeapplication.data.network.Status
import com.example.jokeapplication.databinding.FragmentJokeListBinding
import com.example.jokeapplication.ui.joke_details.JokeDetailsFragment
import com.example.jokeapplication.ui.joke_generate.JokeGenerateFragment
import com.example.jokeapplication.ui.joke_list.recycler.adapter.JokeAdapter
import kotlinx.coroutines.launch

class JokeListFragment : Fragment(R.layout.fragment_joke_list) {

    private val binding: FragmentJokeListBinding by viewBinding(FragmentJokeListBinding::bind)

    private val adapter = JokeAdapter()

    private val manager = JokeManager()

    private lateinit var viewModel: JokeViewModel

    private var fromCached: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createRecyclerViewList()

        viewModel = ViewModelProvider(this)[JokeViewModel::class.java]

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (isScrollToBottom(binding.recyclerView)) {
                    viewModel.fetchNetworkJokes()
                }
            }

            private fun isScrollToBottom(recyclerView: RecyclerView): Boolean {
                return recyclerView.computeVerticalScrollRange() - recyclerView.computeVerticalScrollOffset() <= recyclerView.computeVerticalScrollExtent()
            }
        }
        )

        observeJokes()

        binding.generatejoke.setOnClickListener {
            openGenerateFragment()
        }
    }

    private fun observeJokes() {
        lifecycleScope.launch {
            viewModel.commentState.collect {

                when (it.status) {
                    Status.LOADING -> {
                        binding.shimmerLayout.startShimmer()
                        binding.shimmerLayout.isVisible = true
                        binding.recyclerView.isVisible = false
                    }

                    Status.SUCCESS -> {
                        binding.shimmerLayout.stopShimmer()
                        binding.shimmerLayout.isVisible = false
                        binding.recyclerView.isVisible = true
                        isJokeGenerationRequired()
                    }

                    else -> {
                        binding.shimmerLayout.stopShimmer()
                        binding.shimmerLayout.isVisible = false
                        binding.recyclerView.isVisible = true
                        if (!fromCached) {
                            val jokes = JokesRepository(
                                RetrofitInstance.api,
                                JokeDatabase.INSTANCE.jokeDao(),
                                CacheDatabase.INSTANCE.cacheDao()
                            ).getCache()
                            for (num in jokes) {
                                manager.generator.addNewJokeEnd(
                                    num.category,
                                    num.question,
                                    num.answer,
                                    true
                                )
                            }
                            if (jokes.isNotEmpty()) {
                                Toast.makeText(context, "Load cache jokes", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show()
                            }
                            fromCached = true
                        }
                        isJokeGenerationRequired()
                    }
                }
            }
        }
    }

    private fun isJokeGenerationRequired() {
        val data = manager.data
        if (manager.isDataNull()) {
            binding.emptyListText.text = "Шутки вышли из чата, добавьте новую!"
        } else {
            binding.emptyListText.text = ""
            adapter.setNewData(data)
        }
    }

    private fun openGenerateFragment() {
        val fragment = JokeGenerateFragment()

        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun openDetailsFragment(v: View, jokeID: String) {
        val activity = v.context as AppCompatActivity
        val newFragment = JokeDetailsFragment.newInstance(jokeID)
        activity.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, newFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun createRecyclerViewList() {
        binding.recyclerView.adapter = adapter
    }
}