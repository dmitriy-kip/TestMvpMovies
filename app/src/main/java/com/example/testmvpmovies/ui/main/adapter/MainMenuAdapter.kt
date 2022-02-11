package com.example.testmvpmovies.ui.main.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testmvpmovies.R
import com.example.testmvpmovies.databinding.ItemGenreBinding
import com.example.testmvpmovies.databinding.ItemHeaderBinding
import com.example.testmvpmovies.databinding.ItemMovieBinding
import com.example.testmvpmovies.ui.entities.BaseEntity
import com.example.testmvpmovies.ui.entities.GenreEntity
import com.example.testmvpmovies.ui.entities.HeaderEntity
import com.example.testmvpmovies.utils.ViewTypes
import com.example.testmvpmovies.ui.entities.MovieEntity

class MainMenuAdapter(private val itemClick: (item: BaseEntity) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var infoList = listOf<BaseEntity>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class HeaderViewHolder(private val binding: ItemHeaderBinding): RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun create(parent: ViewGroup): HeaderViewHolder {
                val binding = ItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return HeaderViewHolder(binding)
            }
        }

        fun bind(header: HeaderEntity) {
            binding.headerName.text = binding.root.resources.getString(header.name)
        }
    }

    class GenreViewHolder(
        private val binding: ItemGenreBinding,
        private val itemClick: (genre: GenreEntity) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {
        companion object{
            fun create(parent: ViewGroup, itemClick: (genre: GenreEntity) -> Unit): GenreViewHolder {
                val binding = ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return GenreViewHolder(binding, itemClick)
            }
        }

        fun bind(genre: GenreEntity) {
            if (genre.isClicked) {
                binding.genreContainer.background = ResourcesCompat.getDrawable(binding.root.resources, R.drawable.bg_clicked_genre, null)
            } else {
                binding.genreContainer.background = ResourcesCompat.getDrawable(binding.root.resources, R.drawable.bg_genre, null)
            }
            binding.genreName.text = genre.name
            binding.genreContainer.setOnClickListener {
                itemClick(genre)
            }
        }
    }

    class MovieViewHolder(
        private val binding: ItemMovieBinding,
        private val itemClick: (movie: MovieEntity) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {
        companion object{
            fun create(parent: ViewGroup, itemClick: (genre: MovieEntity) -> Unit): MovieViewHolder {
                val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return MovieViewHolder(binding, itemClick)
            }
        }

        fun bind(movie: MovieEntity) = with(binding){
            movieName.text = movie.localizedName
            Glide.with(root).load(movie.imageUrl).placeholder(R.drawable.placeholder).error(R.drawable.error_image).into(image)
            movieContainer.setOnClickListener {
                itemClick(movie)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewTypes.GENRE_VT.ordinal -> GenreViewHolder.create(parent, itemClick)
            ViewTypes.HEADER_VT.ordinal -> HeaderViewHolder.create(parent)
            else -> MovieViewHolder.create(parent, itemClick)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(infoList[position]) {
            is GenreEntity -> {
                holder as GenreViewHolder
                holder.bind(infoList[position] as GenreEntity)
            }
            is HeaderEntity -> {
                holder as HeaderViewHolder
                holder.bind(infoList[position] as HeaderEntity)
            }
            is MovieEntity -> {
                holder as MovieViewHolder
                holder.bind(infoList[position] as MovieEntity)
            }
        }
    }

    override fun getItemCount() = infoList.size

    override fun getItemViewType(position: Int): Int {
        return when(infoList[position]) {
            is GenreEntity -> ViewTypes.GENRE_VT.ordinal
            is HeaderEntity -> ViewTypes.HEADER_VT.ordinal
            else -> ViewTypes.MOVIE_VT.ordinal
        }
    }
}