package com.starzplay.entertainment.ui.fragments.movies

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.starzplay.entertainment.databinding.FragmentMoviesBinding
import com.starzplay.entertainment.models.UIState
import com.starzplay.entertainment.ui.base.BaseFragment
import com.starzplay.starzlibrary.helper.gone
import com.starzplay.starzlibrary.helper.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MoviesFragment : BaseFragment<FragmentMoviesBinding>(FragmentMoviesBinding::inflate) {

    private val viewModel: MoviesViewModel by viewModels()
    private val moviesAdapter = MoviesAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMovies("")
        setupViews()
        setupObserverListener()
    }

    private fun setupViews() = with(binding) {
        searchView.apply {
            isIconified = false
            clearFocus()
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        viewModel.getMovies(query = query)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    // Handle real-time text changes if needed
                    return false
                }
            })

        }
        carouselRecyclerView.apply {
            adapter = moviesAdapter
        }
    }

    private fun setupObserverListener() = with(viewModel) {

        movies.observe(viewLifecycleOwner) { respons ->
            moviesAdapter.submitList(respons.results.groupBy { it.mediaType }.toSortedMap())
        }

        lifecycleScope.launch {
            uiState.collectLatest { uiState ->
                when (uiState) {
                    is UIState.LoadingState -> {
                        binding.progressBar.show()
                    }

                    is UIState.ContentState, UIState.InitialState -> {
                        binding.progressBar.gone()
                    }

                    is UIState.ErrorState -> {
                        binding.progressBar.gone()
                        showAlertDialog(messages = uiState.message,
                            onPosClick = { _, _ -> },
                            onNegClick = { _, _ -> })
                    }
                }
            }
        }

    }

    private fun navigateToDetail() {
        findNavController().navigate(MoviesFragmentDirections.toDetailFragment())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.movies.removeObservers(this)
    }

}