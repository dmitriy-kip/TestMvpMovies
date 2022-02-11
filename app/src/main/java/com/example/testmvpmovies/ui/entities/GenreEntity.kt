package com.example.testmvpmovies.ui.entities

data class GenreEntity(
    val name: String,
    val isClicked: Boolean = false
) : BaseEntity()
