package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.models.MovieApiService
import com.example.myapplication.models.MovieResponse
import com.example.myapplication.models.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    private lateinit var apiService: MovieApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiService = RetrofitClient.instance.create(MovieApiService::class.java)

        //fetchMovies()

        setContent {
            MyApp()
        }
    }

    private fun fetchMovies() {
        apiService.getMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val movies = response.body()?.movies
                    movies?.forEach { movie ->
                        Log.d("MainActivity", "Movie: ${movie.title}, Release Date: ${movie.releaseDate}")
                    }
                } else {
                    Log.e("MainActivity", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e("MainActivity", "Failed to fetch movies", t)
            }
        })
    }
}

@Composable
fun MyApp() {
    var text by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Ingrese un texto") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (text.isNotEmpty()) {
                context.startActivity(DisplayActivity.newIntent(context, text))
            }
        }) {
            Text("Mostrar texto")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
                val movieListIntent = Intent(context, DisplayActivity1::class.java)
                context.startActivity(movieListIntent)
        }) {
            Text("Mostrar Lista con RV")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyAppPreview() {
    MyApp()
}