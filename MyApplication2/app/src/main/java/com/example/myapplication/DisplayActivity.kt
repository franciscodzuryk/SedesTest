package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.myapplication.models.Movie
import com.example.myapplication.models.MovieApiService
import com.example.myapplication.models.MovieResponse
import com.example.myapplication.models.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DisplayActivity : ComponentActivity() {
    private lateinit var apiService: MovieApiService

    companion object {
        private const val EXTRA_TEXT = "extra_text"

        fun newIntent(context: Context, text: String): Intent {
            return Intent(context, DisplayActivity::class.java).apply {
                putExtra(EXTRA_TEXT, text)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiService = RetrofitClient.instance.create(MovieApiService::class.java)
        setContent {
            MovieApp(apiService)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayTextPreview() {
    MovieApp(apiService = RetrofitClient.instance.create(MovieApiService::class.java))
}

@Composable
fun MovieApp(apiService: MovieApiService) {
    var movies by remember { mutableStateOf<List<Movie>>(emptyList()) }
    LaunchedEffect(Unit) {
        fetchMovies(apiService) { fetchedMovies ->
            movies = fetchedMovies // Actualiza el estado con las películas obtenidas
        }
    }
    MaterialTheme {
        MovieList(movies)
    }
}

private fun fetchMovies(apiService: MovieApiService, onMoviesFetched: (List<Movie>) -> Unit) {
    apiService.getMovies().enqueue(object : Callback<MovieResponse> {
        override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
            if (response.isSuccessful) {
                val fetchedMovies = response.body()?.movies ?: emptyList()
                Log.d("DisplayActivity", "Fetched movies: ${fetchedMovies.size}")
                onMoviesFetched(fetchedMovies) // Llama al lambda con las películas
            } else {
                Log.e("DisplayActivity", "Error: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
            Log.e("DisplayActivity", "Failed to fetch movies", t)
        }
    })
}

@Composable
fun MovieList(movies: List<Movie>) {
    LazyColumn {
        items(movies) { movie ->
            MovieItem(movie)
        }
    }
}

@Composable
fun MovieItem(movie: Movie) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = rememberImagePainter(movie.imageURL),
                contentDescription = movie.title,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = movie.releaseDate,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
