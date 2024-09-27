package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.models.Movie
import com.example.myapplication.models.MovieApiService
import com.example.myapplication.models.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddMovieActivity : ComponentActivity() {

    private lateinit var apiService: MovieApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiService = RetrofitClient.instance.create(MovieApiService::class.java)

        setContent {
            MovieScreen(apiService)
        }
    }
}

@Composable
fun MovieScreen(apiService: MovieApiService) {
    var movieTitle by remember { mutableStateOf("") }
    var movieOverview by remember { mutableStateOf("") }
    var movieReleaseDate by remember { mutableStateOf("") }
    var movieImageURL by remember { mutableStateOf("") }
    val successMessage by remember { mutableStateOf("") }
    val errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = movieTitle,
            onValueChange = { movieTitle = it },
            label = { Text("Title") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = movieOverview,
            onValueChange = { movieOverview = it },
            label = { Text("Overview") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = movieReleaseDate,
            onValueChange = { movieReleaseDate = it },
            label = { Text("Release Date") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = movieImageURL,
            onValueChange = { movieImageURL = it },
            label = { Text("Image URL") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val newMovie = Movie(
                id = 0, // Asume que el ID se genera en el servidor
                title = movieTitle,
                overview = movieOverview,
                releaseDate = movieReleaseDate,
                imageURL = movieImageURL
            )
            addMovie(newMovie, apiService)
        }) {
            Text("Add Movie")
        }

        Spacer(modifier = Modifier.height(16.dp))
        if (successMessage.isNotEmpty()) {
            Text(successMessage, color = MaterialTheme.colorScheme.primary)
        }
        if (errorMessage.isNotEmpty()) {
            Text("Error: $errorMessage", color = MaterialTheme.colorScheme.error)
        }
    }
}

private fun addMovie(movie: Movie, apiService: MovieApiService) {
    apiService.addMovie(movie).enqueue(object : Callback<Void> {
        override fun onResponse(call: Call<Void>, response: Response<Void>) {
            if (response.isSuccessful) {
                Log.d("MovieActivity", "Movie added successfully")
            } else {
                Log.e("MovieActivity", "Error adding movie: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<Void>, t: Throwable) {
            Log.e("MovieActivity", "Failed to add movie", t)
        }
    })
}

@Preview(showBackground = true)
@Composable
fun PreviewMovieScreen() {
    MovieScreen(apiService = RetrofitClient.instance.create(MovieApiService::class.java))
}
