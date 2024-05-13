package com.example.epicfilms.data

data class UiState(
    val popularPages: Int = 0,
    val popular: List<Movie> = arrayListOf(),
    val popularPageError: String? = null,
    val topRatedPages: Int = 0,
    val topRated: List<Movie> = arrayListOf(),
    val topRatedPageError: String? = null,
    val selectedMovie: Movie? = null,
    val favorites: List<Movie> = arrayListOf(),
    val addedFavoRepo: Boolean = false,
    val favoRepo: FavoriteMovieRepository? = null,
    val addedMovieCacheRepo: Boolean = false,
    val movieCacheRepo: MovieCacheRepository? = null,
)
