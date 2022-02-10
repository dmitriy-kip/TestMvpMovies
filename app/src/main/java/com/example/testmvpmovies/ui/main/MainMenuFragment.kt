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
import com.example.testmvpmovies.utils.delay
import com.example.testmvpmovies.utils.isInternetAvailable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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
    private var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkInternet()

        initListeners()
    }

    private fun initListeners() = with(binding) {
        tryAgainButton.setOnClickListener {
            errorText.visibility = View.GONE
            tryAgainButton.visibility = View.GONE
            progressBar.visibility = View.VISIBLE

            presenter.getMovieList()
        }
    }

    //эту часть логики думаю можно оставить во View
    //потому что она требует контекст и если ее разбивать код рискует стать не читаемым
    //а передача контекста в презентр может быть опасна из за утечки памяти
    private fun checkInternet() {
        if (isInternetAvailable(requireActivity().applicationContext)) {
            presenter.getMovieList()
        } else {
            binding.internetErrorText.visibility = View.VISIBLE
            job = delay(CoroutineScope(Dispatchers.IO), 1000, Dispatchers.IO, true) {
                if (isInternetAvailable(requireActivity().applicationContext)) {
                    presenter.getMovieList()
                    job?.cancel()
                }
            }
        }
    }

    override fun listReady(list: List<BaseEntity>) = with(binding) {
        initRecyclerView(list)

        menuRecycler.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        internetErrorText.visibility = View.GONE
    }

    override fun clickMovie(movie: MovieEntity) {
    }

    override fun clickGenre(list: List<BaseEntity>) {
        adapter?.infoList = list
    }

    override fun errorLoading() {
        binding.progressBar.visibility = View.GONE
        binding.errorText.visibility = View.VISIBLE
        binding.tryAgainButton.visibility = View.VISIBLE
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