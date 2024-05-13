package com.example.epicfilms.ui

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.epicfilms.data.APIService
import com.example.epicfilms.data.FavoriteMovieRepository
import com.example.epicfilms.data.Movie
import com.example.epicfilms.data.MovieCacheRepository
import com.example.epicfilms.data.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception

class MoviesViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun loadPagePopular() {
        viewModelScope.launch {
            try {
                val newPage = APIService.getInstance().getPopular(uiState.value.popularPages + 1).results
                val popular = if (uiState.value.popularPages == 0) newPage
                    else (uiState.value.popular + newPage)
                uiState.value.movieCacheRepo?.replaceWith("popular", popular)
                _uiState.update {state ->
                    state.copy(
                        popularPages = state.popularPages + 1,
                        popular = popular,
                        popularPageError = null
                    )
                }
            } catch (e: Exception) {
                val popular = uiState.value.movieCacheRepo?.getOne("popular")
                if (popular !== null) {
                    _uiState.update {state ->
                        state.copy(
                            popularPages = 0,
                            popular = popular,
                            popularPageError = null
                        )
                    }
                } else {
                    _uiState.update {state ->
                        state.copy(
                            popularPageError = "No Internet"
                        )
                    }
                }
            }
        }
    }

    fun loadPageTopRated() {
        viewModelScope.launch {
            try {
                val newPage = APIService.getInstance().getTopRated(uiState.value.topRatedPages + 1).results
                val topRated = if (uiState.value.topRatedPages == 0) newPage
                    else (uiState.value.topRated + newPage)
                uiState.value.movieCacheRepo?.replaceWith("top_rated", topRated)
                _uiState.update { state ->
                    state.copy(
                        topRatedPages = state.topRatedPages + 1,
                        topRated = topRated,
                        topRatedPageError = null
                    )
                }
            } catch (e: Exception) {
                val topRated = uiState.value.movieCacheRepo?.getOne("top_rated")
                if (topRated !== null) {
                    _uiState.update {state ->
                        state.copy(
                            topRatedPages = 0,
                            topRated = topRated,
                            topRatedPageError = null
                        )
                    }
                } else {
                    _uiState.update { state ->
                        state.copy(
                            topRatedPageError = "No Internet"
                        )
                    }
                }

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
            try {
                val movie = APIService.getInstance().getMovie(id)
                _uiState.update { state ->
                    state.copy(
                        selectedMovie = movie
                    )
                }
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    fun favorite(movie: Movie) {
        _uiState.update {state ->
            state.copy(
                favorites = state.favorites + movie
            )
        }
        viewModelScope.launch {
            uiState.value.favoRepo?.addFavorite(movie)
        }
    }

    fun unfavorite(movie: Movie) {
        _uiState.update {state ->
            state.copy(
                favorites = state.favorites.filter { it.id != movie.id }
            )
        }
        viewModelScope.launch {
            uiState.value.favoRepo?.removeFavorite(movie)
        }
    }

    fun addFavoRepo(repo: FavoriteMovieRepository) {
        if (uiState.value.addedFavoRepo) return
        println("Adding favorite repository to uiState...")
        repo.readAllData.observeForever(Observer {
            _uiState.update {state ->
                state.copy(
                    favorites = it.map { it.toMovie() }
                )
            }
        })
        _uiState.update { state ->
            state.copy(
                addedFavoRepo = true,
                favoRepo = repo
            )
        }
    }

    fun addMovieCacheRepo(repo: MovieCacheRepository) {
        if (uiState.value.addedMovieCacheRepo) return
        println("Adding MovieCache repository to uiState...")
        _uiState.update { state ->
            state.copy(
                addedMovieCacheRepo = true,
                movieCacheRepo = repo
            )
        }
    }
}