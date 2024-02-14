package com.dejvidleka.moviehub.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dejvidleka.data.local.models.Genre
import com.dejvidleka.data.local.models.MovieData
import com.dejvidleka.data.local.models.MovieResult
import com.dejvidleka.moviehub.R
import com.dejvidleka.moviehub.databinding.FragmentFirstBinding
import com.dejvidleka.moviehub.domain.Result
import com.dejvidleka.moviehub.ui.adapters.GenreAdapter
import com.dejvidleka.moviehub.ui.adapters.MovieListByGenreAdapter
import com.dejvidleka.moviehub.ui.viewmodels.MainViewModel
import com.dejvidleka.moviehub.utils.MovieClickListener
import com.google.android.material.carousel.CarouselLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
@AndroidEntryPoint
class FirstFragment : Fragment(), MovieClickListener {

    private val mainViewModel: MainViewModel by viewModels()
    private var _binding: FragmentFirstBinding? = null
    private lateinit var genreAdapter: GenreAdapter
    private lateinit var adapter: MovieListByGenreAdapter
    private var searchJob: Job? = null
    private var textQuery: String? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModelData()
        setUpSearch()

    }

    private fun setUpSearch() {
        adapter = MovieListByGenreAdapter(genre = null, onClick = this, hasViewMore = false)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = CarouselLayoutManager()

        val searchView = binding.searchView
        searchView.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                performSearch(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

    }

    fun performSearch(query: String) {
        textQuery = query
        viewModelToSearch()
    }

    private fun viewModelToSearch() {
        searchJob?.cancel()
        searchJob = viewLifecycleOwner.lifecycleScope.launch {
            textQuery?.let {
                mainViewModel.getSearchResult(it).collect { result ->
                    when (result) {
                        is Result.Loading -> {
                        }

                        is Result.Success -> {
                            adapter.submitList(result.data)
                            Log.d("this new List", "${result.data}")
                        }

                        is Result.Error -> {
                            Log.d("this new list", "error")
                        }
                    }
                }
            }
        }
        searchJob?.invokeOnCompletion {
        }
    }

    override fun onMovieClick(movieResult: MovieResult, view: View) {
        val navigation =
            FirstFragmentDirections.actionHomeToMovieDetail(movieResult)
        view.findNavController().navigate(navigation)

    }

    override fun onMovieClickNew(movieData: MovieData, view: View) {
    }

    override fun onViewMoreClick(genre: Genre, view: View) {
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.categoriesRv.layoutManager = layoutManager
        binding.lifecycleOwner = viewLifecycleOwner

        genreAdapter = GenreAdapter(mainViewModel, viewLifecycleOwner)
        binding.categoriesRv.adapter = genreAdapter
        mainViewModel.updateCategory("movie")
        binding.chipCategories.setOnCheckedChangeListener { _, checkedId ->
            val category = when (checkedId) {
                R.id.chip_2_movies_first -> "movie"
                R.id.chip_3_shows_first -> "tv"
                else -> return@setOnCheckedChangeListener
            }
            mainViewModel.updateCategory(category)
        }
    }

    private fun observeViewModelData() {
        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.genre.collect { result ->
                when (result) {
                    is Result.Loading -> {
                    }

                    is Result.Success -> {
                        genreAdapter.submitList(result.data.sortedBy { it.id })
                        Log.d("my list", "${result.data}")
                    }

                    is Result.Error -> {
                        Toast.makeText(requireContext(), "Shame", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
