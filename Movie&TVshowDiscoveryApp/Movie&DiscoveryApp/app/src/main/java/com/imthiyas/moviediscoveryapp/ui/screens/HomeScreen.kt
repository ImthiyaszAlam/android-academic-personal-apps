package com.imthiyas.moviediscoveryapp.ui
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.imthiyas.moviediscoveryapp.data.request.Movie
import com.imthiyas.moviediscoveryapp.navigation.Screen
import com.imthiyas.moviediscoveryapp.viewmodel.MovieViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: MovieViewModel = koinViewModel()) {
    val movies by viewModel.movies.collectAsState()
    val tvShows by viewModel.tvShows.collectAsState()
    val isLoading by viewModel.loading.collectAsState()

    var showMovies by remember { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(onClick = { showMovies = true }) {
                Text("Movies")
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(onClick = { showMovies = false }) {
                Text("TV Shows")
            }
        }

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            val list = if (showMovies) movies else tvShows
            LazyColumn {
                items(list) { movie ->
                    MovieItem(movie, navController)
                }
            }
        }
    }
}

@Composable
fun MovieItem(movie: Movie, navController: NavController) {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = 4.dp,
        modifier = Modifier.fillMaxWidth().padding(8.dp).clickable {
            navController.navigate(Screen.Details.passMovie(movie))
        }
    ) {
        Row {
            Image(
                painter = rememberImagePainter(movie.poster),
                contentDescription = movie.title,
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = movie.title, style = MaterialTheme.typography.h6)
                Text(text = "Year: ${movie.year}", color = Color.Gray)
            }
        }
    }
}
