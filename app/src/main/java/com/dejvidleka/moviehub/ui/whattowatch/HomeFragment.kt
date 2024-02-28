package com.dejvidleka.moviehub.ui.whattowatch

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dejvidleka.data.local.models.Genre
import com.dejvidleka.data.local.models.MovieData
import com.dejvidleka.data.local.models.MovieResult
import com.dejvidleka.moviehub.databinding.FragmentWhatToWatchBinding
import com.dejvidleka.moviehub.domain.Result
import com.dejvidleka.moviehub.ui.adapters.TopMovieAdapter
import com.dejvidleka.moviehub.ui.adapters.TrendingViewPager
import com.dejvidleka.moviehub.ui.viewmodels.MainViewModel
import com.dejvidleka.moviehub.utils.AppPreferences
import com.dejvidleka.moviehub.utils.MovieClickListener
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment(), MovieClickListener {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentWhatToWatchBinding
    private lateinit var topMovieAdapter: TopMovieAdapter
    private lateinit var trendingMovieAdapter: TrendingViewPager
    private val handler = Handler(Looper.getMainLooper())
    private val update = Runnable { }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWhatToWatchBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeUI()
    }

    private fun initializeUI() {
        getTabListeners()
        getProviderName()
        setupAdapters()
        populationTopMovies()
        populationTrendingMovies()
    }

    private fun getTabListeners() {
        binding.chipCategories.addOnTabSelectedListener(
            object :
                TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {}

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.let {
                        val category = when (it.position) {
                            0 -> "movie"
                            1 -> "tv"
                            else -> return
                        }
                        mainViewModel.updateCategory(category)
                    }
                }
            })
        binding.chipCategoriesTopGenres.addOnTabSelectedListener(
            object :
                TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.let {
                        val section = when (it.position) {
                            0 -> ""
                            1 -> "top_rated"
                            2 -> "popular"
                            3 -> "now_playing"
                            else -> return
                        }
                        mainViewModel.updateSection(section = section)
                    }
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
    }

    private fun setupAdapters() {
        val savedRegionCode = AppPreferences.getRegionCode(requireContext())
        topMovieAdapter = TopMovieAdapter(savedRegionCode, requireContext(), this)
        trendingMovieAdapter = TrendingViewPager(this)

        binding.topRatedRv.apply {
            adapter = topMovieAdapter
            layoutManager = LinearLayoutManager(context)
        }
        binding.trendingCarosel.adapter = trendingMovieAdapter
    }

    private fun getProviderName() {
        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.getProviderNames().collectLatest { result ->
                if (result is Result.Success) {
                    Log.d("Providers", result.data.joinToString { it.provider_name })
                }
            }
        }
    }

    private fun populationTopMovies() {
        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.section.collectLatest { section ->
                val targetFlow =
                    if (section == "") mainViewModel.recommendedMovies else mainViewModel.topRatedMovies
                targetFlow.collectLatest { result ->
                    handleMovieResult(
                        result,
                        topMovieAdapter,
                        binding.topRatedRv,
                        binding.placeHolder
                    )
                }
            }
        }
    }

    private fun populationTrendingMovies() {
        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.category.collectLatest { category ->
                mainViewModel.getTrending(category).collectLatest { result ->
                    handleMovieResult(
                        result,
                        trendingMovieAdapter,
                        binding.trendingCarosel,
                        binding.trendingRvPlaceholder.root
                    )
                }
            }
        }
    }

    private fun <T> handleMovieResult(
        result: Result<List<T>>,
        adapter: RecyclerView.Adapter<*>,
        contentView: View,
        placeholder: View
    ) {
        when (result) {
            is Result.Success -> {
                when (adapter) {
                    is TrendingViewPager -> {
                        adapter.submitList(result.data as List<MovieResult>)
                        contentView.visibility = View.VISIBLE
                        placeholder.visibility = View.GONE
                    }

                    is TopMovieAdapter -> {
                        adapter.submitList(result.data as List<MovieData>)
                        contentView.visibility = View.VISIBLE
                        placeholder.visibility = View.GONE
                    }
                }
            }

            is Result.Loading -> {
                when (adapter) {
                    is TopMovieAdapter -> {
                        adapter.submitList(emptyList())
                    }
                    is TrendingViewPager->{adapter.submitList(emptyList())}
                }
                adapter.notifyDataSetChanged()
                contentView.visibility = View.GONE
                placeholder.visibility = View.VISIBLE
            }

            is Result.Error -> {
                when (adapter) {
                    is TopMovieAdapter -> {
                        adapter.submitList(emptyList())
                    }
                    is TrendingViewPager->{adapter.submitList(emptyList())}
                }
                Toast.makeText(requireContext(), "An error occurred", Toast.LENGTH_SHORT).show()
                contentView.visibility = View.GONE
                placeholder.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(update)
    }

    override fun onMovieClick(movieResult: MovieResult, view: View) {
        val navigation =
            HomeFragmentDirections.actionWhatToWatchFragmentToMovieDetailFragment(movieResult)
        view.findNavController().navigate(navigation)
    }

    override fun onMovieClickNew(movieData: MovieData, view: View) {
    }

    override fun onViewMoreClick(genre: Genre, view: View) {
    }
}


