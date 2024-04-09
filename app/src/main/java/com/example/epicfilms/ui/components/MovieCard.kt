package com.example.epicfilms.ui.components

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.rememberAsyncImagePainter
import com.example.epicfilms.data.Movie

@Composable
fun MovieCard(movie: Movie, modifier: Modifier) {
    Box (modifier = modifier.padding(2.dp)) {
        val context = LocalContext.current
        Card(modifier = Modifier.clickable {
            try {
                val uri = "imdb:///title/${movie.imdb_id}"
                println(uri)
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                startActivity(context, intent, null)
            } catch (e: ActivityNotFoundException) {
                println("Activity not found!")
            }
        }) {
            Column(modifier = Modifier.background(Color.hsl(0f, 0f, 0.5f))) {
                Image(
                    painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/original" + movie.poster_path),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(ratio = 1f/1.4141f)
                )
                // Card(modifier = Modifier.fillMaxSize().background(Color.Black)) {
                Row (modifier = Modifier.padding(2.dp)){
                    Text(movie.title, modifier = Modifier.weight(3f), maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Row(modifier = Modifier.weight(2f)) {
                        Text("★", color = Color.hsl(hue = 60f, 0.8f, 0.5f), modifier = Modifier.padding(2.dp))
                        Text("${movie.vote_average}/10", textAlign = TextAlign.Right)
                    }
                }
                // }
            }
        }
    }
}