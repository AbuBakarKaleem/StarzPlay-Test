package com.starzplay.entertainment.ui.fragments.movies

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.starzplay.entertainment.databinding.ItemCarouselBinding
import com.starzplay.entertainment.extension.toSentenceCase
import com.starzplay.entertainment.interfaces.OnMediaClickListener
import com.starzplay.starzlibrary.data.remote.ResponseModel.MoviesData

class MoviesAdapter(
    private val listener: OnMediaClickListener
) : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    private val moviesGroups = mutableListOf<Pair<String, List<MoviesData>>>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: Map<String, List<MoviesData>>) {
        moviesGroups.clear()
        moviesGroups.addAll(newList.toList())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        // Use View Binding to inflate the layout
        val binding =
            ItemCarouselBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val (mediaType, items) = moviesGroups[position]
        holder.bind(mediaType, items, listener)
    }

    override fun getItemCount(): Int = moviesGroups.size

    class MoviesViewHolder(private val binding: ItemCarouselBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(mediaType: String, items: List<MoviesData>, listener: OnMediaClickListener) {
            binding.titleView.text = mediaType.toSentenceCase()
            val mediaAdapter = MediaAdapter(listener)
            binding.apply {
                mediaView.adapter = mediaAdapter
                mediaView.layoutManager =
                    LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)

            }
            mediaAdapter.submitList(items)
        }
    }
}

