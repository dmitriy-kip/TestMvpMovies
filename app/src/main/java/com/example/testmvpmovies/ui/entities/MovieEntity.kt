package com.example.testmvpmovies.ui.entities

data class MovieEntity(
    val id: Int,
    val localizedName: String,
    val name: String,
    val year: Int,
    val rating: Double?,
    val imageUrl: String?,
    val description: String?,
    val genres: List<String>
): BaseEntity()
