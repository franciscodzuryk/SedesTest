package com.example.myapplication.models

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val imageURL: String
)

data class MovieResponse(
    val movies: List<Movie>
)