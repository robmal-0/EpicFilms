package com.example.epicfilms.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_cache")
class MovieCache (
    @PrimaryKey(autoGenerate = false)
    val page: String = "",
    @ColumnInfo(name = "movie_list")
    val movieList: String = ""
)