package com.starzplay.entertainment.ui.fragments.movies

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.starzplay.entertainment.databinding.ItemCarouselBinding
import com.starzplay.entertainment.extension.toSentenceCase
import com.starzplay.starzlibrary.data.remote.ResponseModel.MoviesData

class MediaAdapter(
    private val listener: (MoviesData) -> Unit, private val loadMore: (Boolean) -> Unit
) : RecyclerView.Adapter<MediaAdapter.MoviesViewHolder>() {

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
        return MoviesViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val (mediaType, items) = moviesGroups[position]
        holder.bind(mediaType, items, loadMore)
    }

    override fun getItemCount(): Int = moviesGroups.size

    class MoviesViewHolder(
        private val binding: ItemCarouselBinding, listener: (MoviesData) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private val carousalAdapter = CarousalAdapter(listener)
        fun bind(mediaType: String, items: List<MoviesData>, loadMore: (Boolean) -> Unit) {
            binding.titleView.text = mediaType.toSentenceCase()
            binding.apply {
                carousalView.apply {
                    adapter = carousalAdapter
                    layoutManager =
                        LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                    addOnScrollListener(object : RecyclerView.OnScrollListener() {
                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                            super.onScrolled(recyclerView, dx, dy)
                            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                            if (layoutManager.findLastCompletelyVisibleItemPosition() == carousalAdapter.itemCount - 1) {
                                loadMore.invoke(true)
                            }
                        }
                    })
                }

            }
            if (carousalAdapter.itemCount == 0) carousalAdapter.submitList(items) else {
                carousalAdapter.updateMovies(items)
            }
        }
    }
}

