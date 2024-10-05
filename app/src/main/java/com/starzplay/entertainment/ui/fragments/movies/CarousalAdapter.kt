package com.starzplay.entertainment.ui.fragments.movies

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.starzplay.entertainment.databinding.ItemMediaBinding
import com.starzplay.entertainment.extension.loadImage
import com.starzplay.starzlibrary.data.remote.ResponseModel.MoviesData
import com.starzplay.starzlibrary.helper.gone

class CarousalAdapter(
    private val listener: (MoviesData) -> Unit
) : RecyclerView.Adapter<CarousalAdapter.CarouselViewHolder>() {

    private var moviesData = listOf<MoviesData>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<MoviesData>) {
        moviesData = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        // Inflate the layout using View Binding
        val binding = ItemMediaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarouselViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val movie = moviesData[position]
        holder.bind(movie)

        holder.itemView.setOnClickListener {
            listener.invoke(movie)
        }
    }

    override fun getItemCount(): Int = moviesData.size

    class CarouselViewHolder(private val binding: ItemMediaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MoviesData) {
            val imageUrl = when (movie.mediaType) {
                "person" -> movie.profilePath
                "tv", "movie" -> movie.posterPath
                else -> movie.posterPath
            }
            imageUrl?.let {
                binding.root.context.loadImage(it, binding.mediaImage)
                binding.imageProgressView.gone()
            } ?: binding.imageProgressView.gone()
        }
    }

    // Use DiffUtil to update the list efficiently
    fun updateMovies(newMovies: List<MoviesData>) {
        val diffCallback = MoviesDiffCallback(moviesData, newMovies)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        // Update the current list and dispatch updates to the adapter
        moviesData = newMovies
        diffResult.dispatchUpdatesTo(this)
    }
}

class MoviesDiffCallback(
    private val oldList: List<MoviesData>, private val newList: List<MoviesData>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // Compare unique IDs
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // Compare the contents of the items
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
