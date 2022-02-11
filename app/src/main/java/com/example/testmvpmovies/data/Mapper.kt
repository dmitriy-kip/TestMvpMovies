package com.example.testmvpmovies.data

import com.example.testmvpmovies.data.network.entities.MovieNetworkEntity
import com.example.testmvpmovies.ui.entities.MovieEntity

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