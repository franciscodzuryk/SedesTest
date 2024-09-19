package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.models.Movie
import com.squareup.picasso.Picasso

class DisplayActivity1 : ComponentActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var movieList: List<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Suponiendo que tienes una lista de películas
        movieList = listOf(
            Movie(1, "Movie Title 1", "Overview of movie 1", "2023-01-01", "https://cdn.shopify.com/s/files/1/0852/5250/5906/files/joker.jpg"),
            Movie(2, "Movie Title 2", "Overview of movie 2", "2023-02-02", "https://cdn.shopify.com/s/files/1/0852/5250/5906/files/joker.jpg")
            // Agrega más películas aquí
        )

        movieAdapter = MovieAdapter(movieList)
        recyclerView.adapter = movieAdapter

    }
}

class MovieAdapter(private val movies: List<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val overviewTextView: TextView = view.findViewById(R.id.overviewTextView)
        val releaseDateTextView: TextView = view.findViewById(R.id.releaseDateTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.titleTextView.text = movie.title
        holder.overviewTextView.text = movie.overview
        holder.releaseDateTextView.text = movie.releaseDate
        Picasso.get().load(movie.imageURL).into(holder.imageView)
    }

    override fun getItemCount() = movies.size
}