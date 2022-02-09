package com.example.testmvpmovies.data.network

import com.example.testmvpmovies.data.network.entities.ListMovieNetworkEntity
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("films.json")
    fun getMovies(): Call<ListMovieNetworkEntity>
}