package com.starzplay.entertainment.ui.fragments.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.starzplay.entertainment.databinding.ItemMediaBinding
import com.starzplay.entertainment.extension.loadImage
import com.starzplay.starzlibrary.data.remote.ResponseModel.MediaData
import com.starzplay.starzlibrary.helper.gone

class CarousalAdapter(
    private val listener: (MediaData) -> Unit
) : RecyclerView.Adapter<CarousalAdapter.CarouselViewHolder>() {

    private var mediaData = listOf<MediaData>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<MediaData>) {
        mediaData = newList
        notifyDataSetChanged()
    }

    fun getItem(position: Int) = mediaData[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        // Inflate the layout using View Binding
        val binding = ItemMediaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarouselViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val movie = mediaData[position]
        holder.bind(movie)

        holder.itemView.setOnClickListener {
            listener.invoke(movie)
        }
    }

    override fun getItemCount(): Int = mediaData.size

    class CarouselViewHolder(private val binding: ItemMediaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MediaData) {
            val imageUrl = when (movie.mediaType) {
                "person" -> movie.profilePath
                "tv", "movie" -> movie.posterPath ?: movie.backdropPath ?: ""
                else -> movie.posterPath
            }
            imageUrl?.let {
                binding.root.context.loadImage(it, binding.mediaImage, binding.imageProgressView)
            } ?: binding.imageProgressView.gone()
        }
    }

    fun updateMedia(newMovies: List<MediaData>) {
        val diffCallback = CarosalDiffCallback(mediaData, newMovies)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        mediaData = newMovies
        diffResult.dispatchUpdatesTo(this)
    }
}

class CarosalDiffCallback(
    private val oldList: List<MediaData>, private val newList: List<MediaData>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
