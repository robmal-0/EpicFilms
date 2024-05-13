package com.example.epicfilms.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieCacheDao {
    @Query("SELECT * FROM movie_cache WHERE page = :page")
    suspend fun getOne(page: String): MovieCache?

    @Query("DELETE FROM movie_cache")
    suspend fun clearCache()

    @Insert
    suspend fun insert(cache: MovieCache)
}