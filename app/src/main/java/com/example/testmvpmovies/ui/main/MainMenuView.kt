package com.example.testmvpmovies.ui.main

import com.example.testmvpmovies.ui.entities.BaseEntity
import com.example.testmvpmovies.ui.entities.MovieEntity
import moxy.MvpView
import moxy.viewstate.strategy.alias.*

interface MainMenuView: MvpView {
    @SingleState
    fun listReady(list: List<BaseEntity>)
    @Skip
    fun clickMovie(movie: MovieEntity)
    @AddToEnd
    fun clickGenre(list: List<BaseEntity>)
    @AddToEnd
    fun errorLoading()
    @AddToEnd
    fun checkInternet()
}