package com.example.epicfilms.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movie")
class FavoriteMovie(
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,
    @ColumnInfo(name = "popularity")
    val popularity: Float = 0f,
    @ColumnInfo(name = "vote_average")
    val vote_average: Float = 0f,
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "imdb_id")
    val imdb_id: String = "",
    @ColumnInfo(name = "homepage")
    val homepage: String = "",
    @ColumnInfo(name = "backdrop_path")
    val backdrop_path: String = "",
    @ColumnInfo(name = "poster_path")
    val poster_path: String = "",
    @ColumnInfo(name = "overview")
    val overview: String = ""
) {
    fun toMovie(): Movie {
        return Movie (
            id = this.id,
            popularity = this.popularity,
            vote_average = this.vote_average,
            title = this.title,
            imdb_id = this.imdb_id,
            homepage = this.homepage,
            backdrop_path = this.backdrop_path,
            poster_path = this.poster_path,
            overview = this.overview,
        )
    }

    companion object {
        fun fromMovie(movie: Movie): FavoriteMovie {
            return FavoriteMovie (
                id = movie.id,
                popularity = movie.popularity,
                vote_average = movie.vote_average,
                title = movie.title,
                imdb_id = movie.imdb_id,
                homepage = movie.homepage,
                backdrop_path = movie.backdrop_path,
                poster_path = movie.poster_path,
                overview = movie.overview,
            )
        }
    }
}