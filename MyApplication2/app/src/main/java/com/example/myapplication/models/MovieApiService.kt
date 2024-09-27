package com.example.myapplication.models
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MovieApiService {
    @GET("movie")
    fun getMovies(): Call<MovieResponse>

    @POST("movie")
    fun addMovie(@Body movie: Movie): Call<Void>

}