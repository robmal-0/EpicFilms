package com.example.epicfilms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.epicfilms.ui.MovieList
import com.example.epicfilms.ui.MoviesViewModel
import com.example.epicfilms.ui.theme.EpicFilmsTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.epicfilms.data.Genre
import com.example.epicfilms.data.Movie

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

@Composable
fun App(
    modifier: Modifier = Modifier,
    viewModel: MoviesViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = SelectedScreen.valueOf(
        backStackEntry?.destination?.route ?: SelectedScreen.Popular.name
    )

    Scaffold (
        topBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentScreen.title == R.string.popular,
                    onClick = { navController.navigate(SelectedScreen.Popular.name) },
                    label = { Text(stringResource(R.string.popular))},
                    icon = {}
                )
                NavigationBarItem(
                    selected = currentScreen.title == R.string.top_rated,
                    onClick = { navController.navigate(SelectedScreen.TopRated.name) },
                    label = { Text(stringResource(R.string.top_rated))},
                    icon = {}
                )
                NavigationBarItem(
                    selected = currentScreen.title == R.string.favorite,
                    onClick = { navController.navigate(SelectedScreen.Favorite.name) },
                    label = { Text(stringResource(R.string.favorite)) },
                    icon = {}
                )
            }
        }
    ) {
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = SelectedScreen.Popular.name,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(it)
        ) {
            composable(route = SelectedScreen.Popular.name) {
                MovieList(movieList = uiState.popular)
            }
            composable(route = SelectedScreen.TopRated.name) {
                MovieList(movieList = uiState.topRated)
            }
            composable(route = SelectedScreen.Favorite.name) {
                Text(stringResource(R.string.favorite))
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