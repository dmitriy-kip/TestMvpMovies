package com.example.testmvpmovies.utils

import android.view.View
import android.widget.TextView
import com.example.testmvpmovies.R

fun TextView.hideIfNullWhenSetText(rating: Double?) {
    if (rating == null) {
        this.visibility = View.INVISIBLE
    } else {
        this.text = this.context.resources.getString(R.string.rating_movie, rating.toString())
    }
}