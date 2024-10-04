package com.starzplay.entertainment.ui.fragments.movies

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.starzplay.entertainment.databinding.ItemMediaBinding
import com.starzplay.entertainment.extension.loadImage
import com.starzplay.entertainment.interfaces.OnMediaClickListener
import com.starzplay.starzlibrary.data.remote.ResponseModel.MoviesData
import com.starzplay.starzlibrary.helper.gone

class MediaAdapter(
    private val listener: OnMediaClickListener
) : RecyclerView.Adapter<MediaAdapter.CarouselViewHolder>() {

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
            listener.onMediaClick(movie)
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
                binding.root.context.loadImage(it, binding.mediaImage, binding.imageProgressView)
            } ?: binding.imageProgressView.gone()
        }
    }
}
