package com.example.testmvpmovies.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testmvpmovies.MovieEntity
import com.example.testmvpmovies.databinding.FragmentMainMenuBinding
import moxy.MvpAppCompatFragment

class MainMenuFragment : MvpAppCompatFragment(), MainMenuView {
    private var _binding: FragmentMainMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun listReady(list: List<MovieEntity>?) {
        TODO("Not yet implemented")
    }

    override fun clickMovie(movie: MovieEntity) {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}