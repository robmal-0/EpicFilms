package com.example.epicfilms.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

class FavoriteMovieRepository(private val favoriteDao: FavoriteDao) {
    var readAllData: LiveData<List<FavoriteMovie>> = favoriteDao.getAll()

    suspend fun addFavorite(movie: Movie) {
        favoriteDao.insert(FavoriteMovie.fromMovie(movie))
    }

    suspend fun removeFavorite(movie: Movie) {
        favoriteDao.delete(movie.id)
    }
}
