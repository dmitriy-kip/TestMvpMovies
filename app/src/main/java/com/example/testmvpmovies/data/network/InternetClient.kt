package com.example.testmvpmovies.data.network

import com.example.testmvpmovies.data.network.entities.ListMovieNetworkEntity
import com.example.testmvpmovies.data.network.entities.MovieNetworkEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class InternetClient(retrofit: Retrofit) {
    private val apiService = retrofit.create(ApiService::class.java)

    fun getMovies(result:(List<MovieNetworkEntity>?) -> Unit) {
        val client: Call<ListMovieNetworkEntity> = apiService.getMovies()
        client.enqueue(object : Callback<ListMovieNetworkEntity> {
            override fun onResponse(
                call: Call<ListMovieNetworkEntity>,
                response: Response<ListMovieNetworkEntity>
            ) {
                if (response.isSuccessful) {
                    val responseObject = response.body()
                    val listMovie = responseObject?.films
                    result(listMovie)
                } else {
                    result(null)
                }
            }

            override fun onFailure(call: Call<ListMovieNetworkEntity>, t: Throwable) {
                result(null)
            }

        })
    }
}