package com.imthiyas.moviediscoveryapp.ui.screens


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.imthiyas.moviediscoveryapp.data.request.Movie

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailsScreen(movie: Movie) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(movie.title) })
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberImagePainter(movie.poster),
                contentDescription = movie.title,
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Title: ${movie.title}", style = MaterialTheme.typography.h6)
            Text(text = "Year: ${movie.year}", style = MaterialTheme.typography.body1)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = movie.title, style = MaterialTheme.typography.body2)
        }
    }
}
