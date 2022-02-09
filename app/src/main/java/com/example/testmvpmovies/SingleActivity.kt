package com.example.testmvpmovies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.testmvpmovies.data.DataManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SingleActivity : AppCompatActivity() {

    @Inject
    lateinit var dataManager: DataManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single)

        dataManager.getMovies {
            Log.e("TEST", it.toString())
        }
    }
}