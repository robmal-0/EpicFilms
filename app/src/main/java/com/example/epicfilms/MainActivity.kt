package com.example.epicfilms

import android.content.Context
import android.graphics.drawable.Icon
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.getSystemService
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.epicfilms.ui.MovieList
import com.example.epicfilms.ui.MoviesViewModel
import com.example.epicfilms.ui.theme.EpicFilmsTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.epicfilms.data.FavoriteMovieRepository
import com.example.epicfilms.data.MovieCacheRepository
import com.example.epicfilms.data.MovieDatabase
import com.example.epicfilms.ui.MoviePage
import com.example.epicfilms.ui.NoInternetPage
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

enum class SelectedScreen(@StringRes val title: Int) {
    Popular(title = R.string.popular),
    TopRated(title = R.string.top_rated),
    Favorite(title = R.string.favorite),
    Selected(title = R.string.select)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EpicFilmsTheme {
                // A surface container using the 'background' color from the theme
                App(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
@Composable
fun App(
    modifier: Modifier = Modifier,
    viewModel: MoviesViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = SelectedScreen.valueOf(
        backStackEntry?.destination?.route ?: SelectedScreen.Popular.name
    )
    val uiState by viewModel.uiState.collectAsState()

    val db = MovieDatabase.getInstance(LocalContext.current)
    val favMovieRepo = FavoriteMovieRepository(db.favoriteDao())
    viewModel.addFavoRepo(favMovieRepo)
    val movieCacheRepo = MovieCacheRepository(db.movieCacheDao())
    viewModel.addMovieCacheRepo(movieCacheRepo)

    LocalContext.current.unregisterCallback()

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    if (currentScreen != SelectedScreen.Selected) {
                        Text(stringResource(currentScreen.title))
                    } else {
                        Text(uiState.selectedMovie?.title ?: "Error")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.hsl(190f, 0.3f, 0.5f))
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentScreen == SelectedScreen.Popular,
                    onClick = {
                        navController.navigate(SelectedScreen.Popular.name)
                        if (uiState.popular.isNotEmpty()) {
                            GlobalScope.launch {
                                uiState.movieCacheRepo?.replaceWith("popular", uiState.popular)
                            }
                        }
                    },
                    label = { Text(stringResource(R.string.popular))},
                    icon = {
                        Icon(imageVector = Icons.Filled.Star, contentDescription = "Popular")
                    }
                )
                NavigationBarItem(
                    selected = currentScreen == SelectedScreen.TopRated,
                    onClick = {
                        navController.navigate(SelectedScreen.TopRated.name)
                        if (uiState.topRated.isNotEmpty()) {
                            GlobalScope.launch {
                                uiState.movieCacheRepo?.replaceWith("top_rated", uiState.topRated)
                            }
                        }
                    },
                    label = { Text(stringResource(R.string.top_rated))},
                    icon = {
                        Icon(imageVector = Icons.Filled.ThumbUp, contentDescription = "Popular")
                    }
                )
                NavigationBarItem(
                    selected = currentScreen == SelectedScreen.Favorite,
                    onClick = { navController.navigate(SelectedScreen.Favorite.name) },
                    label = { Text(stringResource(R.string.favorite)) },
                    icon = {
                        Icon(imageVector = Icons.Filled.Favorite, contentDescription = "Popular")
                    }
                )
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = SelectedScreen.Popular.name,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(it)
        ) {
            composable(route = SelectedScreen.Popular.name) {
                if (uiState.popularPages == 0) {
                    viewModel.loadPagePopular()
                }
                if (uiState.popularPageError !== null) {
                    NoInternetPage(modifier = Modifier.fillMaxSize())
                    LocalContext.current.registerCallback { state ->
                        if (state == ConnectionState.Available) {
                            viewModel.loadPageTopRated()
                        }
                    }
                }
                MovieList(movieList = uiState.popular, navController = navController, viewModel = viewModel)
            }
            composable(route = SelectedScreen.TopRated.name) {
                if (uiState.topRatedPages == 0) {
                    viewModel.loadPageTopRated()
                }
                if (uiState.topRatedPageError !== null) {
                    NoInternetPage(modifier = Modifier.fillMaxSize())
                    LocalContext.current.registerCallback { state ->
                        if (state == ConnectionState.Available) {
                            viewModel.loadPageTopRated()
                        }
                    }
                }
                MovieList(movieList = uiState.topRated, navController = navController, viewModel = viewModel)
            }
            composable(route = SelectedScreen.Favorite.name) {
                MovieList(movieList = uiState.favorites, navController = navController, viewModel = viewModel)
            }
            composable(route = SelectedScreen.Selected.name) {
                if (uiState.selectedMovie != null) {
                    MoviePage(navController = navController, viewModel = viewModel)
                } else {
                    Text("Error while loading movie...")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    EpicFilmsTheme {
        App(modifier = Modifier.fillMaxSize())
    }
}