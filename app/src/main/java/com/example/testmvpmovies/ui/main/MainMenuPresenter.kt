package com.example.testmvpmovies.ui.main

import com.example.testmvpmovies.data.DataManager
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class MainMenuPresenter @Inject constructor(private val dataManager: DataManager): MvpPresenter<MainMenuView>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        dataManager.getMovies { list ->
            viewState.listReady(list)
        }
    }
}