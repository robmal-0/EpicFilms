package com.example.epicfilms.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter

@Composable
fun MoviePage(
    navController: NavHostController,
    viewModel: MoviesViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val movie = uiState.selectedMovie!!
    if (movie.imdb_id == "") {
        println(movie.backdrop_path)
        viewModel.getDetails(movie.id)
    }
    Column(modifier = modifier.fillMaxSize()) {
        Image(
            painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/original" + movie.backdrop_path),
            contentDescription = "Movie Backdrop",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Text(movie.title, fontSize = 40.sp)
        LazyRow {
            items (movie.genres) {
                Card(modifier = Modifier.padding(horizontal = 3.dp)) {
                    Text(it.name, modifier = Modifier.padding(horizontal = 4.dp))
                }
            }
        }
        Text(movie.overview, modifier = Modifier.padding(horizontal = 6.dp))
        Button(onClick = {
            try {
                val uri = "imdb:///title/${movie.imdb_id}"
                println(uri)
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                ContextCompat.startActivity(context, intent, null)
            } catch (e: ActivityNotFoundException) {
                println("Activity not found!")
            }
        }) {
            Text("Show on IMDb")
        }
        if (movie.homepage != "") {
            Button(onClick = {
                try {
                    val uri = movie.homepage
                    println(uri)
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                    val chooser = Intent.createChooser(intent, uri)
                    ContextCompat.startActivity(context, chooser, null)
                } catch (e: ActivityNotFoundException) {
                    println("Activity not found!")
                }
            }) {
                Text("Open movies webpage")
            }
        }
    }
}