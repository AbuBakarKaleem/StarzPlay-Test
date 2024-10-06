package com.starzplay.entertainment.ui.fragments.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.starzplay.entertainment.models.UIState
import com.starzplay.starzlibrary.data.remote.DataState
import com.starzplay.starzlibrary.data.remote.ResponseModel.MediaData
import com.starzplay.starzlibrary.data.usecases.FetchMediaDataUseCase
import com.starzplay.starzlibrary.data.usecases.FetchMultiSearchUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel : ViewModel() {
    private val multiSearchUseCase = FetchMultiSearchUseCase()
    private val mediaDataUseCase = FetchMediaDataUseCase()

    private val _multiSearch = MutableLiveData<MutableMap<String, MutableList<MediaData>>>()
    val multiSearch: LiveData<MutableMap<String, MutableList<MediaData>>> = _multiSearch

    private val _mediaTypeData = MutableLiveData<List<MediaData>>()
    val mediaTypeData: LiveData<List<MediaData>> = _mediaTypeData

    private val _uiState = MutableStateFlow<UIState>(UIState.InitialState)
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    var loadMorePosition = -1

    init {
        getMovies()
    }

    fun getMovies(query: String = "All", page: Int = 1) {
        _uiState.value = UIState.LoadingState
        viewModelScope.launch(Dispatchers.IO) {
            multiSearchUseCase.invoke(searchQuery = query, pageNo = page).collect { dataState ->
                withContext(Dispatchers.Main) {
                    when (dataState) {
                        is DataState.Success -> {
                            _uiState.value = UIState.ContentState
                            dataState.data?.let { response ->
                                val sortedMutableMap: MutableMap<String, MutableList<MediaData>> =
                                    response.results.groupBy { it.mediaType }  // Group by mediaType
                                        .mapValues { it.value.toMutableList() }  // Convert each List to MutableList
                                        .toSortedMap()  // Convert to SortedMap
                                        .toMutableMap()
                                _multiSearch.value = sortedMutableMap
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

    fun getMediaData(query: String = "All", page: Int = 1, mediaType: String) {
        _uiState.value = UIState.LoadingState
        viewModelScope.launch(Dispatchers.IO) {
            mediaDataUseCase.invoke(searchQuery = query, pageNo = page, mediaType = mediaType)
                .collect { dataState ->
                    withContext(Dispatchers.Main) {
                        when (dataState) {
                            is DataState.Success -> {
                                _uiState.value = UIState.ContentState
                                dataState.data?.let {
                                    _mediaTypeData.value = it
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