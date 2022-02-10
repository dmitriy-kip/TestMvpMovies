package com.example.testmvpmovies.ui.main

import android.util.Log
import com.example.testmvpmovies.R
import com.example.testmvpmovies.data.DataManager
import com.example.testmvpmovies.ui.entities.BaseEntity
import com.example.testmvpmovies.ui.entities.GenreEntity
import com.example.testmvpmovies.ui.entities.HeaderEntity
import com.example.testmvpmovies.ui.entities.MovieEntity
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class MainMenuPresenter @Inject constructor(private val dataManager: DataManager): MvpPresenter<MainMenuView>() {

    private var infoList: List<BaseEntity>? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        dataManager.getMovies { movieList ->
            if (movieList == null) {
                viewState.listReady(null)
                return@getMovies
            }

            val movies = movieList.sortedBy { it.localizedName }

            val resultList = mutableListOf<BaseEntity>()
            resultList.add(HeaderEntity(R.string.genre))
            resultList.addAll(getGenreList(movies))
            resultList.add(HeaderEntity(R.string.movie))
            resultList.addAll(movies)

            this.infoList = resultList
            viewState.listReady(resultList)
        }
    }

    fun itemClick(item: BaseEntity) {
        if (item is MovieEntity) {
            viewState.clickMovie(item)
        } else {
            val clickedInfoList = mutableListOf<BaseEntity>()
            for (infoItem in infoList!!) {
                if (infoItem == item) {
                    infoItem as GenreEntity
                    clickedInfoList.add(infoItem.copy(isClicked = true))
                    continue
                }
                if (infoItem is MovieEntity && !infoItem.genres.contains((item as GenreEntity).name)) {
                    continue
                }
                clickedInfoList.add(infoItem)
            }
            viewState.clickGenre(clickedInfoList)
        }
    }

    private fun getGenreList(list: List<MovieEntity>): List<GenreEntity> {
        val genreListTmp = mutableListOf<String>()
        for (item in list) {
            genreListTmp.addAll(item.genres)
        }
        val genreListTmpDistinct = genreListTmp.distinct()
        val genreList = mutableListOf<GenreEntity>()
        for (item in genreListTmpDistinct) {
            genreList.add(GenreEntity(item))
        }
        return genreList
    }


}