package com.example.epicfilms.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.epicfilms.data.Movie
import com.example.epicfilms.ui.components.MovieCard

@Composable
fun MovieList(movieList: ArrayList<Movie>, modifier: Modifier = Modifier) {
    val list = movieList.toMutableList()
    Column(modifier = modifier) {
        while (list.size > 0) {
            Row {
                val first = list.removeAt(0)
                MovieCard(first, modifier = Modifier.weight(1f))
                if (list.size > 0) {
                    val second = list.removeAt(0)
                    MovieCard(second, modifier = Modifier.weight(1f))
                }
            }
        }
    }
}