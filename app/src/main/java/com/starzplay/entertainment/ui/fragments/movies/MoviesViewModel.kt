package com.starzplay.entertainment.ui.fragments.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.starzplay.entertainment.models.UIState
import com.starzplay.starzlibrary.data.remote.DataState
import com.starzplay.starzlibrary.data.remote.ResponseModel.Movies
import com.starzplay.starzlibrary.data.usecases.FetchMoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesViewModel : ViewModel() {
    private val moviesUseCase = FetchMoviesUseCase()

    private val _movies = MutableLiveData<Movies>()
    val movies: LiveData<Movies> = _movies

    private val _uiState = MutableStateFlow<UIState>(UIState.InitialState)
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    init {
        getMovies()
    }

    fun getMovies(query: String = "All", page: Int = 1) {
        _uiState.value = UIState.LoadingState
        viewModelScope.launch(Dispatchers.IO) {
            moviesUseCase.invoke(searchQuery = query, pageNo = page).collect { dataState ->
                withContext(Dispatchers.Main) {
                    when (dataState) {
                        is DataState.Success -> {
                            _uiState.value = UIState.ContentState
                            dataState.data?.let {
                                _movies.value = it
                            }
                        }

                        is DataState.Error -> {
                            _uiState.value = UIState.ErrorState(dataState.error.message)
                        }
                    }
                }
            }
        }
    }
}