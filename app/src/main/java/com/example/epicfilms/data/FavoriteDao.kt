package com.example.epicfilms.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite_movie")
    fun getAll(): LiveData<List<FavoriteMovie>>

    @Insert
    suspend fun insert(movie: FavoriteMovie)

    @Query("DELETE FROM favorite_movie WHERE id = :id")
    suspend fun delete(id: Int)

}