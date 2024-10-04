package com.starzplay.entertainment.ui.fragments.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.starzplay.entertainment.databinding.FragmentDetailBinding
import com.starzplay.entertainment.extension.getImageUrl
import com.starzplay.entertainment.ui.base.BaseFragment
import com.starzplay.starzlibrary.data.remote.ResponseModel.MoviesData

class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var selectedMovie: MoviesData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedMovie = DetailFragmentArgs.fromBundle(it).selectedMovie
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupListeners()
    }

    private fun setupListeners() {

    }

    private fun setupViews() = with(binding) {
        title.text = selectedMovie.title
        description.text = selectedMovie.overview
        val imageUrl = requireContext().getImageUrl(selectedMovie.posterPath!!)
        Glide.with(requireContext()).load(imageUrl) // URL of the image you want to load
            .into(detailImage)
    }
}