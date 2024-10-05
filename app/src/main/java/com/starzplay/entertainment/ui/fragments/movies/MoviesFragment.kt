package com.starzplay.entertainment.ui.fragments.movies

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.starzplay.entertainment.databinding.FragmentMoviesBinding
import com.starzplay.entertainment.models.DetailInfo
import com.starzplay.entertainment.models.UIState
import com.starzplay.entertainment.ui.base.BaseFragment
import com.starzplay.starzlibrary.data.remote.ResponseModel.MoviesData
import com.starzplay.starzlibrary.helper.gone
import com.starzplay.starzlibrary.helper.show
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MoviesFragment : BaseFragment<FragmentMoviesBinding>(FragmentMoviesBinding::inflate) {

    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var mediaAdapter: MediaAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupObserverListener()
    }

    private fun setupViews() = with(binding) {
        mediaAdapter =
            MediaAdapter(listener = { movieData -> navigateToDetail(movieData) }, loadMore = {
                val query = searchView.query?.takeIf { it.isNotEmpty() } ?: "All"
                val currentPage = viewModel.movies.value?.page ?: 1

                viewModel.getMovies(query = query.toString(), page = currentPage.plus(1))
            })
        searchView.apply {
            isIconified = false
            clearFocus()
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (!query.isNullOrEmpty()) viewModel.getMovies(query = query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })

        }
        carouselRecyclerView.apply {
            adapter = mediaAdapter
        }
    }

    private fun setupObserverListener() = with(viewModel) {

        movies.observe(viewLifecycleOwner) { respons ->
            if (mediaAdapter.itemCount == 0) mediaAdapter.submitList(respons.results.groupBy { it.mediaType }
                .toSortedMap()) else {
                lifecycleScope.launch {
                    delay(100)
                    mediaAdapter.submitList(respons.results.groupBy { it.mediaType })
                }
            }
        }

        lifecycleScope.launch {
            uiState.collectLatest { uiState ->
                when (uiState) {
                    is UIState.LoadingState -> {
                        showLoading()
                    }

                    is UIState.ContentState, UIState.InitialState -> {
                        hideLoading()
                    }

                    is UIState.ErrorState -> {
                        hideLoading()
                        showAlertDialog(messages = uiState.message,
                            onPosClick = { _, _ -> },
                            onNegClick = { _, _ -> })
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.movies.removeObservers(this)
    }

    private fun showLoading() {
        binding.apply {
            //carouselRecyclerView.gone()
            progressBar.show()
        }
    }

    private fun hideLoading() {
        binding.apply {
            progressBar.gone()
            //carouselRecyclerView.show()
        }
    }

    private fun navigateToDetail(movie: MoviesData) {
        val detailInfo = DetailInfo().apply {
            mediaType = movie.mediaType
            title = movie.mediaType
            when (movie.mediaType) {
                "person" -> {
                    description = movie.name
                    imageUrl = movie.profilePath ?: ""
                }

                "tv", "movie" -> {
                    description = movie.overview ?: ""
                    imageUrl = movie.posterPath ?: ""
                }

                else -> {
                    //do nothing
                }
            }
        }
        findNavController().navigate(
            MoviesFragmentDirections.toDetailFragment(detailInfo)
        )
    }

}