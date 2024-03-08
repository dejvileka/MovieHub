package com.dejvidleka.moviehub.ui.whattowatch

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dejvidleka.data.local.models.Genre
import com.dejvidleka.data.local.models.MovieData
import com.dejvidleka.data.local.models.MovieResult
import com.dejvidleka.moviehub.databinding.FragmentWhatToWatchBinding
import com.dejvidleka.moviehub.domain.Result
import com.dejvidleka.moviehub.ui.adapters.RecommendedMovieAdapter
import com.dejvidleka.moviehub.ui.adapters.TopMovieAdapter
import com.dejvidleka.moviehub.ui.adapters.TrendingViewPager
import com.dejvidleka.moviehub.ui.viewmodels.MainViewModel
import com.dejvidleka.moviehub.utils.AppPreferences
import com.dejvidleka.moviehub.utils.MovieClickListener
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment(), MovieClickListener {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentWhatToWatchBinding
    private lateinit var topMovieAdapter: TopMovieAdapter
    private lateinit var recommendedMovieAdapter: RecommendedMovieAdapter
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
        viewLifecycleOwner.lifecycleScope.launch {
            setupAdapters()
        }
        getTabListeners()
        getProviderName()
        populationTrendingMovies()
        populationTopMovies()

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

    private fun getProviderName() {
        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.getProviderNames().collectLatest { result ->
                if (result is Result.Success) {
                    Log.d("Providers", result.data.joinToString { it.provider_name })
                }
            }
        }
    }

    private suspend fun setupAdapters() {
        val savedRegionCode = AppPreferences.getRegionCode(requireContext())
        topMovieAdapter = TopMovieAdapter(savedRegionCode, requireContext(), this)
        recommendedMovieAdapter = RecommendedMovieAdapter(savedRegionCode, requireContext(), this)
        trendingMovieAdapter = TrendingViewPager(this)
        binding.trendingCarosel.apply {
            adapter = trendingMovieAdapter
        }
        mainViewModel.section.collectLatest {
            if (it == "") {
                binding.topRatedRv.apply {
                    adapter = recommendedMovieAdapter
                    layoutManager = LinearLayoutManager(context)
                }
            } else {
                binding.topRatedRv.apply {
                    adapter = topMovieAdapter
                    layoutManager = LinearLayoutManager(context)
                }
            }
        }
    }

    private fun populationTopMovies() {
        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.section.collectLatest{ section ->
                if (section.isEmpty()) {
                    mainViewModel.recommendedMoviesPagerData()
                        .collect{ result ->
                        handleMovieResult(
                            result,
                            recommendedMovieAdapter,
                            binding.topRatedRv,
                            binding.placeHolder
                        )
                    }
                } else {
                    mainViewModel.topRatedMovies.collect { result ->
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
    }

    private fun populationTrendingMovies() {
        trendingMovieAdapter = TrendingViewPager(this)
        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.category.collectLatest { category ->
                mainViewModel.getTrending(category).collect { result ->
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

    private suspend fun <T> handleMovieResult(
        result: Result<T>,
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
                        Log.d("trending List", (result.data as List<MovieData>).toString())
                    }

                    is RecommendedMovieAdapter -> {
                        adapter.submitData(result.data as PagingData<MovieData>)
                        Log.d("Recommended List", (result.data as PagingData<MovieData>).toString())
                        contentView.visibility = View.VISIBLE
                        placeholder.visibility = View.GONE
                    }

                    is TopMovieAdapter -> {
                        adapter.submitList(result.data as List<MovieData>)
                        contentView.visibility = View.VISIBLE
                        placeholder.visibility = View.GONE
                        Log.d("Top List", (result.data as List<MovieData>).toString())
                    }
                }
            }
            is Result.Loading -> {
                when (adapter) {
                    is TrendingViewPager -> {
//                        trendingMovieAdapter.notifyDataSetChanged()
//                        trendingMovieAdapter.submitList(emptyList())
//                        contentView.visibility = View.VISIBLE
//                        placeholder.visibility = View.GONE
                    }

                    is RecommendedMovieAdapter -> {
//                        recommendedMovieAdapter.submitData(PagingData.empty())
//                        recommendedMovieAdapter.notifyDataSetChanged()
//                        contentView.visibility = View.GONE
//                        placeholder.visibility = View.VISIBLE
                    }

                    is TopMovieAdapter -> {
//                        topMovieAdapter.submitList(emptyList())
//                        topMovieAdapter.notifyDataSetChanged()
//                        contentView.visibility = View.GONE
//                        placeholder.visibility = View.VISIBLE
                    }
                }
            }
            is Result.Error -> {
            }
        }
    }

    override fun onMovieClick(movieResult: MovieResult, view: View) {
        val navigation =
            HomeFragmentDirections.actionWhatToWatchFragmentToMovieDetailFragment(movieResult)
        view.findNavController().navigate(navigation)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(update)
    }

    override fun onMovieClickNew(movieData: MovieData, view: View) {
    }

    override fun onViewMoreClick(genre: Genre, view: View) {
    }
}


