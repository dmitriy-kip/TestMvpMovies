package com.example.testmvpmovies.ui.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.testmvpmovies.R
import com.example.testmvpmovies.databinding.FragmentMovieBinding
import com.example.testmvpmovies.ui.entities.MovieEntity

class MovieFragment : Fragment() {
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = MovieFragmentArgs.fromBundle(requireArguments())

        initViews(args.movie)
        initListeners()
    }

    private fun initViews(movie: MovieEntity) = with(binding) {
        Glide.with(requireContext())
            .load(movie.imageUrl)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error_image)
            .into(posterImage)

        toolBar.title = movie.localizedName
        nameMovie.text = movie.name
        yearMovie.text = resources.getString(R.string.year_movie, movie.year)
        ratingMovie.text = resources.getString(R.string.rating_movie, movie.rating.toString())
        descriptionMovie.text = movie.description
    }

    private fun initListeners() = with(binding) {
        toolBar.setNavigationOnClickListener {
            requireActivity().findNavController(R.id.mainFragmentContainer).popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}