package com.example.epicfilms.ui.components

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.epicfilms.SelectedScreen
import com.example.epicfilms.data.Movie
import com.example.epicfilms.ui.MoviesViewModel

@Composable
fun MovieCard(
    movie: Movie,
    modifier: Modifier,
    navController: NavHostController,
    viewModel: MoviesViewModel
) {
    Box (modifier = modifier) {
        Card(modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp).shadow(3.dp, shape = AbsoluteCutCornerShape(5.dp)).clickable {
            viewModel.selectMovie(movie)
            navController.navigate(SelectedScreen.Selected.name)
        }) {
            Row(modifier = Modifier) {
                Image(
                    painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/original" + movie.poster_path),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(ratio = 1f/1.3f)
                        .weight(1f)
                        .shadow(5.dp)
                )
                // Card(modifier = Modifier.fillMaxSize().background(Color.Black)) {
                Column (modifier = Modifier.padding(2.dp).weight(3f)) {

                    Row (modifier = Modifier){
                        Text(movie.title, modifier = Modifier.weight(3f), maxLines = 1, overflow = TextOverflow.Ellipsis)
                        Row(modifier = Modifier.weight(2f), horizontalArrangement = Arrangement.End) {
                            Icon(imageVector = Icons.Filled.Star, contentDescription = "Star", tint = Color.hsl(hue = 60f, 0.8f, 0.4f))
                            Text("${String.format("%.1f", movie.vote_average)}/10", textAlign = TextAlign.Right)
                        }
                    }

                    Text(movie.overview, modifier = Modifier.padding(horizontal = 4.dp), fontSize = 14.sp, maxLines = 4, overflow = TextOverflow.Ellipsis)
                }

                // }
            }
        }
    }
}