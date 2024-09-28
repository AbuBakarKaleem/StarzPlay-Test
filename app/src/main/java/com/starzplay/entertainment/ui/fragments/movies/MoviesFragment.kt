package com.starzplay.entertainment.ui.fragments.movies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.starzplay.entertainment.databinding.FragmentMoviesBinding
import com.starzplay.entertainment.ui.base.BaseFragment

class MoviesFragment : BaseFragment<FragmentMoviesBinding>(FragmentMoviesBinding::inflate) {

    private val viewModel: MoviesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dummayText.setOnClickListener {
            navigateToDetail()
        }
    }

    private fun navigateToDetail() {
        findNavController().navigate(MoviesFragmentDirections.toDetailFragment())
    }

}