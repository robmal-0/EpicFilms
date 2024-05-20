package com.example.epicfilms.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.epicfilms.data.FavoriteMovieRepository
import com.example.epicfilms.data.Movie
import com.example.epicfilms.ui.components.MovieCard

@Composable
fun MovieList(
    movieList: List<Movie>,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: MoviesViewModel,
) {
    Column(modifier = modifier) {
        var i = 0
        val COLS = 1
        while (i < movieList.size) {
            Row(modifier = Modifier.fillMaxWidth()) {
                var j = 0
                while (j < COLS && i < movieList.size) {
                    MovieCard(
                        movieList[i],
                        modifier = Modifier.weight(1f),
                        navController = navController,
                        viewModel = viewModel
                    )
                    j++
                    i++
                }
            }
        }
    }
}