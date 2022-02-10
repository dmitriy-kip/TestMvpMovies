package com.example.testmvpmovies.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testmvpmovies.ui.entities.MovieEntity
import com.example.testmvpmovies.databinding.FragmentMainMenuBinding
import com.example.testmvpmovies.ui.entities.BaseEntity
import com.example.testmvpmovies.ui.main.adapter.MainMenuAdapter
import com.example.testmvpmovies.utils.ViewTypes
import dagger.hilt.android.AndroidEntryPoint
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class MainMenuFragment : MvpAppCompatFragment(), MainMenuView {
    private var _binding: FragmentMainMenuBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var presenterProvider: Provider<MainMenuPresenter>
    private val presenter by moxyPresenter { presenterProvider.get() }

    private var onItemClick = { item: BaseEntity ->
        presenter.itemClick(item)
    }

    private var adapter: MainMenuAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun listReady(list: List<BaseEntity>?) {
        if (list == null) {
            return
        }

        initRecyclerView(list)

        binding.menuRecycler.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    override fun clickMovie(movie: MovieEntity) {
        TODO("Not yet implemented")
    }

    override fun clickGenre(list: List<BaseEntity>) {
        adapter?.infoList = list
    }

    private fun initRecyclerView(list: List<BaseEntity>) {
        adapter = MainMenuAdapter{ onItemClick(it) }
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when(adapter?.getItemViewType(position)) {
                    ViewTypes.GENRE_VT.ordinal -> 2
                    ViewTypes.HEADER_VT.ordinal -> 2
                    else -> 1
                }
            }
        }
        binding.menuRecycler.layoutManager = gridLayoutManager
        binding.menuRecycler.adapter = adapter
        adapter?.infoList = list
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}