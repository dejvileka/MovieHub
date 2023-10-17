package com.dejvidleka.moviehub.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dejvidleka.data.local.models.Genre
import com.dejvidleka.data.local.models.MovieResult
import com.dejvidleka.moviehub.databinding.FragmentFirstBinding
import com.dejvidleka.moviehub.domain.Result
import com.dejvidleka.moviehub.ui.adapters.GenreAdapter
import com.dejvidleka.moviehub.ui.viewmodels.MainViewModel
import com.dejvidleka.moviehub.utils.OnMovieClickListener
import com.google.android.material.transition.MaterialContainerTransform
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
@AndroidEntryPoint
class FirstFragment : Fragment() , OnMovieClickListener {

    private val mainViewModel: MainViewModel by viewModels()
    private var _binding: FragmentFirstBinding? = null
    private lateinit var genreAdapter: GenreAdapter

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val transform = MaterialContainerTransform().apply {
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
            duration = 1000
        }
        sharedElementReturnTransition = transform


        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupRecyclerView()
        observeViewModelData()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.categoriesRv.layoutManager = layoutManager
        binding.lifecycleOwner = viewLifecycleOwner
         genreAdapter = GenreAdapter(
            mainViewModel = mainViewModel,
            lifecycleOwner = viewLifecycleOwner,
             clickListener = this)

        // Set this adapter to your RecyclerView
        binding.categoriesRv.adapter = genreAdapter
    }

    private fun observeViewModelData() {
        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.genres.collect { genres ->
                when (genres) {
                    is Result.Loading -> {
                        binding.dimView.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.VISIBLE
                        delay(2000)
                        Toast.makeText(requireContext(), "Wait", Toast.LENGTH_SHORT).show()
                    }
                    is Result.Success -> {
                        binding.dimView.visibility = View.GONE
                        binding.progressBar.visibility = View.GONE
                        genreAdapter.submitList(genres.data.sortedBy { it.id })
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

    override fun onMovieClick(movieResult: MovieResult, view: View) {
        val directions =
            FirstFragmentDirections.actionHomeToMovieDetail(movieResult)
        findNavController().navigate(directions)
    }

    override fun onViewMoreClick(genre: Genre, view: View) {
        val directions =
            FirstFragmentDirections.actionFirstFragmentToMoreMoviesPerGenre(genre)
        findNavController().navigate(directions)
    }
}
