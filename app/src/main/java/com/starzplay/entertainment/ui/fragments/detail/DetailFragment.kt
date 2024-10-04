package com.starzplay.entertainment.ui.fragments.detail

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.starzplay.entertainment.databinding.FragmentDetailBinding
import com.starzplay.entertainment.extension.loadImage
import com.starzplay.entertainment.extension.longToast
import com.starzplay.entertainment.extension.toSentenceCase
import com.starzplay.entertainment.models.DetailInfo
import com.starzplay.entertainment.ui.base.BaseFragment
import com.starzplay.starzlibrary.helper.gone

class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var selectedMovie: DetailInfo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedMovie = DetailFragmentArgs.fromBundle(it).selectedMovieInfo
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupListeners()
    }

    private fun setupListeners() = with(binding) {
        playButton.setOnClickListener {
            longToast("Play video")
        }
        backView.setOnClickListener { findNavController().navigateUp() }
    }

    private fun setupViews() = with(binding) {
        title.text = selectedMovie.title.toSentenceCase()
        description.text = selectedMovie.description
        playButton.isVisible = selectedMovie.mediaType != "person"

        selectedMovie.imageUrl.takeIf { it.isNotEmpty() }?.let {
            requireContext().loadImage(it, detailImage, imageProgressView)
        } ?: imageProgressView.gone()
    }
}