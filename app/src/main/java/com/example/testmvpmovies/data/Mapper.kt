package com.example.testmvpmovies.data

import com.example.testmvpmovies.MovieEntity
import com.example.testmvpmovies.data.network.entities.MovieNetworkEntity

fun MovieNetworkEntity.toMovieEntity() = MovieEntity(
    id,
    localized_name,
    name,
    year,
    rating,
    image_url,
    description,
    genres
)