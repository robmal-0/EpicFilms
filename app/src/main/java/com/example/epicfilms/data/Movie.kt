package com.example.epicfilms.data

class Genre (
    val id: Int = 0,
    val name: String = ""
)

class Movie(
    val id: Int = 0,
    val popularity: Float = 0f,
    val vote_average: Float = 0f,
    val genres: ArrayList<Genre> = arrayListOf(),
    val title: String = "",
    val imdb_id: String = "",
    val homepage: String = "",
    val backdrop_path: String = "",
    val poster_path: String = "",
    val overview: String = ""
)