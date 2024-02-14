package com.dejvidleka.moviehub.ui.search

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
import com.dejvidleka.moviehub.databinding.FragmentSearchBinding
import com.dejvidleka.moviehub.domain.Result
import com.dejvidleka.moviehub.ui.adapters.MovieListByGenreAdapter
import com.dejvidleka.moviehub.utils.MovieClickListener
import com.google.android.material.carousel.CarouselLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(), MovieClickListener {
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: MovieListByGenreAdapter
    private var textQuery: String? = null
    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MovieListByGenreAdapter(genre = null, onClick = this, hasViewMore = false)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager =CarouselLayoutManager()

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
        observeViewModelData()
    }

    private fun observeViewModelData() {
        searchJob?.cancel()
        searchJob = viewLifecycleOwner.lifecycleScope.launch {
            textQuery?.let {
                viewModel.getSearchResult(it).collect { result ->
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
            SearchFragmentDirections.actionSearchFragmentToMovieDetailFragment(movieResult)
        view.findNavController().navigate(navigation)

    }

    override fun onMovieClickNew(movieData: MovieData, view: View) {
    }

    override fun onViewMoreClick(genre: Genre, view: View) {
    }
}