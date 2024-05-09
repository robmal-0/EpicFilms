package com.example.epicfilms.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.epicfilms.data.APIService
import com.example.epicfilms.data.Movie
import com.example.epicfilms.data.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MoviesViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun loadPagePopular() {
        viewModelScope.launch {
            _uiState.update {state ->
                state.copy(
                    popularPages = state.popularPages + 1,
                    popular = state.popular + APIService.getInstance().getPopular(state.popularPages + 1).results
                )
            }
        }
    }

    fun loadPageTopRated() {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    topRatedPages = state.topRatedPages + 1,
                    topRated = state.topRated + APIService.getInstance().getTopRated(state.topRatedPages + 1).results
                )
            }
        }
    }

    fun selectMovie(movie: Movie) {
        _uiState.update { state ->
            state.copy(selectedMovie = movie)
        }
    }

    fun getDetails(id: Int) {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    selectedMovie = APIService.getInstance().getMovie(id)
                )
            }
        }
    }

    fun favorite(movie: Movie) {
        _uiState.update {state ->
            state.copy(
                favorites = state.favorites + movie
            )
        }
    }

    fun unfavorite(movie: Movie) {
        _uiState.update {state ->
            state.copy(
                favorites = state.favorites.filter { it.id != movie.id }
            )
        }
    }
}