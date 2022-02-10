package com.example.testmvpmovies.ui.main

import com.example.testmvpmovies.MovieEntity
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd
import moxy.viewstate.strategy.alias.Skip

interface MainMenuView: MvpView {
    @AddToEnd
    fun listReady(list: List<MovieEntity>?)
    @Skip
    fun clickMovie(movie: MovieEntity)
}