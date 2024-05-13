package com.example.epicfilms.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MovieCacheRepository(private val movieCacheDao: MovieCacheDao) {

    suspend fun getOne(page: String = "popular"): List<Movie>? {
        val list = movieCacheDao.getOne(page)?.movieList
        val type = object : TypeToken<List<Movie>>() {}.type
        return Gson().fromJson(list, type)
    }

    suspend fun replaceWith(page: String, movies: List<Movie>) {
        movieCacheDao.clearCache()
        movieCacheDao.insert(MovieCache(
            page = page,
            movieList = Gson().toJson(movies)
        ))
    }

}