package com.dejvidleka.moviehub.ui.whattowatch

import android.graphics.Rect
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
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.viewpager2.widget.ViewPager2
import com.dejvidleka.data.local.models.Genre
import com.dejvidleka.data.local.models.MovieResult
import com.dejvidleka.moviehub.databinding.FragmentWhatToWatchBinding
import com.dejvidleka.moviehub.domain.Result
import com.dejvidleka.moviehub.ui.adapters.MovieListByGenreAdapter
import com.dejvidleka.moviehub.ui.adapters.TrendingViewPager
import com.dejvidleka.moviehub.ui.viewmodels.MainViewModel
import com.dejvidleka.moviehub.utils.MovieClickListener
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment(), MovieClickListener {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentWhatToWatchBinding
    private lateinit var topMovieAdapter: MovieListByGenreAdapter
    private lateinit var trendingMovieAdapter: TrendingViewPager
    private val handler = Handler(Looper.getMainLooper())
    private val update = Runnable { }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWhatToWatchBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.chipCategories.addOnTabSelectedListener(
            object :
                TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

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
                            0 -> "top_rated"
                            1 -> "popular"
                            2 -> "now_playing"
                            else -> return
                        }
                        mainViewModel.updateSection(section = section)
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

            })
//        populationTopMovies()
        populateCard()

    }

    //    private fun populationTopMovies(){
//        topMovieAdapter= MovieListByGenreAdapter(this)
//        binding.topRatedRv.adapter=topMovieAdapter
//        binding.topRatedRv.layoutManager= GridLayoutManager(context,2)
//        viewLifecycleOwner.lifecycleScope.launch {
//            mainViewModel.topRatedMovies.collect { result ->
//                when (result) {
//                    is Result.Success -> {
//                        Log.d("top list", result.data.toString())
//                        topMovieAdapter.submitList(result.data)
//                    }
//                    is Result.Error -> {
//                        Toast.makeText(requireContext(), "Shame", Toast.LENGTH_SHORT).show()
//                    }
//                    is Result.Loading -> {
//                    }
//                }
//            }
//        }
//    }
    private fun populateCard() {
        trendingMovieAdapter = TrendingViewPager(this)
        binding.trendingCarosel.adapter = trendingMovieAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.category.collectLatest {
                mainViewModel.getTrending(category = it).collect { result ->

                    when (result) {
                        is Result.Success -> {
                            Log.d("trending", result.data.toString())
                            trendingMovieAdapter.submitList(result.data)
                        }

                        is Result.Error -> {
                            Toast.makeText(requireContext(), "Shame", Toast.LENGTH_SHORT).show()
                        }

                        is Result.Loading -> {
                        }
                    }
                }
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

    override fun onViewMoreClick(genre: Genre, view: View) {
    }

}


class MarginItemDecoration(private val marginSize: Int) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = marginSize
        outRect.right = marginSize
        outRect.bottom = marginSize

        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = marginSize
        }
    }
}

