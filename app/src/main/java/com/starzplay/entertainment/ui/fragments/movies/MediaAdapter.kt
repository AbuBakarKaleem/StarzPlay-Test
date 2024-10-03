package com.starzplay.entertainment.ui.fragments.movies

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.starzplay.entertainment.databinding.ItemMediaBinding
import com.starzplay.starzlibrary.data.remote.ResponseModel.MoviesData
import javax.inject.Inject

class MediaAdapter @Inject constructor() : RecyclerView.Adapter<MediaAdapter.CarouselViewHolder>() {

    private var moviesData = listOf<MoviesData>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<MoviesData>) {
        moviesData = newList
        notifyDataSetChanged() // Notify the adapter that the data set has changed
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        // Inflate the layout using View Binding
        val binding = ItemMediaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarouselViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        holder.bind(moviesData[position])
    }

    override fun getItemCount(): Int = moviesData.size

    class CarouselViewHolder(private val binding: ItemMediaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MoviesData) {
            Glide.with(binding.root.context).load(movie.posterPath).into(binding.mediaImage)
        }
    }
}