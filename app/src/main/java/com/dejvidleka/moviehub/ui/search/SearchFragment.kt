
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
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dejvidleka.moviehub.databinding.FragmentSearchBinding
import com.dejvidleka.moviehub.domain.Result
import com.dejvidleka.moviehub.ui.adapters.SearchMovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: SearchMovieAdapter
    private var textQuery:String?= null
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
        adapter = SearchMovieAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

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
        textQuery=query
        observeViewModelData()
    }

    private fun observeViewModelData() {

        searchJob?.cancel()
        searchJob = viewLifecycleOwner.lifecycleScope.launch {
            textQuery?.let {
                viewModel.getSearchResult(it).collect { result ->
                    when (result) {
                        is Result.Loading -> {
                            Toast.makeText(context, "Wait for it", Toast.LENGTH_SHORT).show()
                        }

                        is Result.Success -> {
                            Toast.makeText(context, "Its here yaaii", Toast.LENGTH_SHORT).show()
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
            Toast.makeText(context, "Search completed or was cancelled", Toast.LENGTH_SHORT).show()
        }
    }
}