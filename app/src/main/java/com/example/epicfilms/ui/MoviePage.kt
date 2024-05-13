package com.example.epicfilms.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.overscroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.epicfilms.data.FavoriteMovieRepository

@Composable
fun MoviePage(
    navController: NavHostController,
    viewModel: MoviesViewModel,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val movie = uiState.selectedMovie!!
    if (movie.imdb_id == "" || movie.genres.isEmpty()) {
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
        Box(modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
        ) {
            Text(movie.title, fontSize = 40.sp, overflow = TextOverflow.Visible, softWrap = false)
        }
        Box(modifier = Modifier
            .fillMaxWidth()
        ) {
            LazyRow(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp), horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                items(movie.genres) {
                    Card {
                        Text(it.name, fontSize = 20.sp, modifier = Modifier.padding(horizontal = 6.dp))
                    }
                }
            }
        }
        Text(movie.overview, modifier = Modifier.padding(horizontal = 6.dp))
        val favorite = movie.id in uiState.favorites.map { it.id }
        if (favorite) {
            Icon(imageVector = Icons.Filled.Favorite, contentDescription = "unfavorite", modifier = Modifier.clickable {
                viewModel.unfavorite(movie)
            })
        } else {
            Icon(imageVector = Icons.Filled.FavoriteBorder, contentDescription = "favorite", modifier = Modifier.clickable {
                viewModel.favorite(movie)
            })
        }

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