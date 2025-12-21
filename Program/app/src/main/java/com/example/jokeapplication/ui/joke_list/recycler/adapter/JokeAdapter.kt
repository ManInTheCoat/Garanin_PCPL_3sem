package com.example.jokeapplication.ui.joke_list.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.jokeapplication.data.Joke
import com.example.jokeapplication.databinding.JokeItemBinding
import com.example.jokeapplication.ui.joke_list.JokeListFragment
import com.example.jokeapplication.ui.joke_list.recycler.JokeViewHolder


class JokeAdapter : Adapter<JokeViewHolder>() {

    private var data = emptyList<Joke>()

    fun setNewData(newData: List<Joke>) {
        data = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = JokeItemBinding.inflate(inflater)
        return JokeViewHolder(binding).apply {
            binding.root.setOnClickListener { v ->
                val jokeID = getID(data[adapterPosition])
                JokeListFragment().openDetailsFragment(v, jokeID)
            }
        }
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.bind(data[position])
    }

}