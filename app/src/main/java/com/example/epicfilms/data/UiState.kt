package com.example.epicfilms.data

data class UiState(
    val popularPages: Int = 0,
    val popular: List<Movie> = arrayListOf(),
    val topRatedPages: Int = 0,
    val topRated: List<Movie> = arrayListOf(),
    val selectedMovie: Movie? = null
)
