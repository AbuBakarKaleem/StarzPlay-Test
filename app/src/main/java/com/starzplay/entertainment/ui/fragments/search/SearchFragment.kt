package com.starzplay.entertainment.ui.fragments.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.starzplay.entertainment.databinding.FragmentSearchBinding
import com.starzplay.entertainment.models.DetailInfo
import com.starzplay.entertainment.models.UIState
import com.starzplay.entertainment.ui.base.BaseFragment
import com.starzplay.starzlibrary.data.remote.ResponseModel.MediaData
import com.starzplay.starzlibrary.helper.gone
import com.starzplay.starzlibrary.helper.show
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var mediaAdapter: MediaAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    override fun onResume() {
        super.onResume()
        setupObserverListener()
    }

    private fun setupViews() = with(binding) {
        mediaAdapter = MediaAdapter(listener = { movieData -> navigateToDetail(movieData) },
            loadMore = { mediaData, position ->
                loadMore(mediaData, position)
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
        mediaRecyclerView.apply {
            adapter = mediaAdapter
        }
    }

    private fun setupObserverListener() = with(viewModel) {

        multiSearch.observe(viewLifecycleOwner) { response ->
            mediaAdapter.submitList(response)
        }
        mediaTypeData.observe(viewLifecycleOwner) { res ->
            if (res.isNotEmpty()) {
                mediaAdapter.updateCarousalItemList(
                    viewModel.loadMorePosition, newList = res
                )
            }
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

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.multiSearch.removeObservers(this)
        viewModel.mediaTypeData.removeObservers(this)
    }

    private fun navigateToDetail(movie: MediaData) {
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
                    imageUrl = movie.backdropPath ?: movie.posterPath ?: ""
                }

                else -> {
                    //do nothing
                }
            }
        }
        findNavController().navigate(
            SearchFragmentDirections.toDetailFragment(detailInfo)
        )
    }

    private fun loadMore(mediaData: MediaData, position: Int) {
        val query = binding.searchView.query?.takeIf { it.isNotEmpty() } ?: "All"
        with(viewModel) {
            loadMorePosition = position
            getMediaData(
                query = query.toString(),
                page = mediaData.pageNo.plus(1),
                mediaType = mediaData.mediaType
            )
        }
    }

}