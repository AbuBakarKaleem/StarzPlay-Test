package com.starzplay.entertainment.ui.fragments.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.starzplay.entertainment.databinding.ItemMediaBinding
import com.starzplay.entertainment.extension.toSentenceCase
import com.starzplay.starzlibrary.data.remote.ResponseModel.MediaData

class MediaAdapter(
    private val listener: (MediaData) -> Unit, private val loadMore: (MediaData, Int) -> Unit
) : RecyclerView.Adapter<MediaAdapter.SearchViewHolder>() {

    private val mediaGroups = mutableListOf<Pair<String, MutableList<MediaData>>>()
    private var searchViewHolder: SearchViewHolder? = null

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: MutableMap<String, MutableList<MediaData>>) {
        mediaGroups.clear()
        mediaGroups.addAll(newList.toList())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        // Use View Binding to inflate the layout
        val binding =
            ItemMediaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        searchViewHolder = SearchViewHolder(binding)
        return searchViewHolder!!
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val (mediaType, items) = mediaGroups[position]
        holder.bind(mediaType, items, position, loadMore, listener)
    }

    override fun getItemCount(): Int = mediaGroups.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateCarousalItemList(
        updateOnPosition: Int, newList: List<MediaData>
    ) {
        val oldCount = mediaGroups[updateOnPosition].second.size
        mediaGroups[updateOnPosition].second.addAll(newList)
        searchViewHolder?.let {
            it.binding.carousalView.adapter!!.notifyItemChanged(
                oldCount.minus(1)
            )
        }
    }

    class SearchViewHolder(
        val binding: ItemMediaBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            mediaType: String,
            items: List<MediaData>,
            itemPosition: Int,
            loadMore: (MediaData, Int) -> Unit,
            listener: (MediaData) -> Unit
        ) {
            val carousalAdapter = CarousalAdapter(listener)
            binding.apply {
                titleView.text = mediaType.toSentenceCase()
                carousalView.apply {
                    adapter = carousalAdapter
                    layoutManager =
                        LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)

                    addOnScrollListener(object : RecyclerView.OnScrollListener() {
                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                            super.onScrolled(recyclerView, dx, dy)
                            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                            if (layoutManager.findLastCompletelyVisibleItemPosition() == carousalAdapter.itemCount - 1) {
                                loadMore.invoke(
                                    carousalAdapter.getItem(carousalAdapter.itemCount - 1),
                                    itemPosition
                                )
                            }
                        }
                    })
                }
                carousalAdapter.updateMedia(items)
            }
        }
    }

}

