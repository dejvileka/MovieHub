package com.dejvidleka.moviehub.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dejvidleka.moviehub.R
import com.dejvidleka.moviehub.databinding.FragmentFirstBinding
import com.dejvidleka.moviehub.ui.adapters.GenreAdapter
import com.dejvidleka.moviehub.ui.adapters.MovieListByGenreAdapter
import com.dejvidleka.moviehub.ui.viewmodels.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FirstFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()

    private var _binding: FragmentFirstBinding? = null
    private lateinit var genreAdapter: GenreAdapter
    private lateinit var moviesAdapter: MovieListByGenreAdapter
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.categoriesRv.layoutManager = layoutManager
        binding.lifecycleOwner = viewLifecycleOwner


        genreAdapter = GenreAdapter(mainViewModel ,viewLifecycleOwner)
        binding.categoriesRv.adapter = genreAdapter



        // Observe ViewModel data using flows
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            mainViewModel.genre.collect { genres ->
                genreAdapter.submitList(genres)
                Toast.makeText(requireContext(), "hello", Toast.LENGTH_SHORT).show()
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            mainViewModel.loading.collect { isLoading ->
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            mainViewModel.error.collect { errorMessage ->
                if (!errorMessage.isNullOrEmpty()) {
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}