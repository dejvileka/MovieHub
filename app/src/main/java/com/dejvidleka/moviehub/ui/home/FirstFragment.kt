package com.dejvidleka.moviehub.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dejvidleka.data.network.models.Genre
import com.dejvidleka.moviehub.databinding.FragmentFirstBinding
import com.dejvidleka.moviehub.domain.Result
import com.dejvidleka.moviehub.ui.adapters.GenreAdapter
import com.dejvidleka.moviehub.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
@AndroidEntryPoint
class FirstFragment : Fragment() {

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

        genreAdapter = GenreAdapter(mainViewModel, viewLifecycleOwner)
        binding.categoriesRv.adapter = genreAdapter
    }

    private fun observeViewModelData() {
        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.genres.collect { genres ->
                when (genres) {
                    is Result.Loading -> {
                        Toast.makeText(requireContext(), "Wait", Toast.LENGTH_SHORT).show()
                    }
                    is Result.Success -> {
                        genreAdapter.submitList(genres.data.sortedBy { it.id })
                    }
                    is Result.Error -> {
                        Toast.makeText(requireContext(), "Shame", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.loading.collect { isLoading ->
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
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
