package com.example.epicfilms.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.epicfilms.data.Movie
import com.example.epicfilms.ui.components.MovieCard

@Composable
fun MovieList(
    movieList: List<Movie>,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: MoviesViewModel
) {
    Column(modifier = modifier) {
        for (movie in movieList) {
            MovieCard(movie, modifier = Modifier, navController = navController, viewModel = viewModel)
        }
    }
}