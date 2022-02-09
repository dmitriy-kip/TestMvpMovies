package com.example.testmvpmovies.data

import com.example.testmvpmovies.MovieEntity
import com.example.testmvpmovies.data.network.InternetClient
import javax.inject.Inject

class DataManager @Inject constructor (private val client: InternetClient) {

    fun getMovies(result:(List<MovieEntity>?) -> Unit) = client.getMovies {
        if (it != null) {
            result( it.map { movieNetworkEntity -> movieNetworkEntity.toMovieEntity() })
        } else {
            result (null)
        }
    }
}