package com.sigmotoa.reto1

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import coil.compose.AsyncImage
import com.sigmotoa.reto1.model.MovieDbClient
import com.sigmotoa.reto1.model.MovieDbResult

/**
 *
 * Created by sigmotoa on 19/12/23.
 * @author sigmotoa
 *
 * www.sigmotoa.com
 */

@Composable
fun GeneralMoviesScreen(region: String) {
    Log.d("Location", region)
    //val context = LocalContext.current
    var movies by remember {
        mutableStateOf<List<MovieDbResult>>(emptyList())
    }


    LaunchedEffect(true) {
        val result = MovieDbClient.service.listPopularMovies(BuildConfig.API_KEY, region)
        movies = result.results
    }

    var movieSelected by remember {
        mutableStateOf<MovieDbResult?>(null)
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = movies
        ) { item ->

            Movie(item, onClickMovie = { movieSelected = item }) {
            }
        }
    }

    movieSelected?.let {
        MovieDetailScreen(item = it) {
            movieSelected = null
        }
    }

}


@Composable
fun MovieDetailScreen(item: MovieDbResult, onBackClick: () -> Unit = {}) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .aspectRatio(1.5f),
            model = "https://image.tmdb.org/t/p/w500/${item.backdrop_path}",
            contentScale = ContentScale.Crop,
            contentDescription = item.title

        )
        Text(
            text = item.title,
            fontSize = 24.sp,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = item.overview, fontSize = 18.sp, fontStyle = FontStyle.Italic)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = item.popularity.toString(), fontSize = 18.sp, fontStyle = FontStyle.Italic)

        Text(text = buildSpannedString { bold { append("Language: ") } .appendLine(item.original_language) }.toString())


        IconButton(onClick = { onBackClick() }) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = "back"
            )

        }
    }
}


@Composable
fun Movie(movie: MovieDbResult, onClickMovie: () -> Unit, onBackClick: () -> Unit) {
    Column(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.Center) {
        ElevatedCard(
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
            modifier = Modifier
                .size(240.dp)
                .clickable {
                    onClickMovie()
                    onBackClick()

                })
        {
            AsyncImage(
                modifier = Modifier.size(200.dp),
                model = "https://image.tmdb.org/t/p/w185/${movie.poster_path}",
                contentScale = ContentScale.Crop,
                contentDescription = movie.title
            )
            Text(text = movie.title, fontSize = 18.sp, fontStyle = FontStyle.Italic, maxLines = 1, overflow = TextOverflow.Ellipsis)

        }
    }

}

