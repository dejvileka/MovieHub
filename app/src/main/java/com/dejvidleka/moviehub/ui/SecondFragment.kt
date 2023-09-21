package com.dejvidleka.moviehub.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.dejvidleka.moviehub.R
import com.dejvidleka.moviehub.databinding.FragmentMovieDetailBinding
import com.dejvidleka.moviehub.databinding.FragmentMovieDetailBindingImpl
import com.dejvidleka.moviehub.ui.home.FirstFragmentDirections
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class SecondFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView?.visibility = View.GONE
        val args = SecondFragmentArgs.fromBundle(requireArguments())
        val movieResult = args.movieResult
        binding.movieTitle.text = movieResult.title
        binding.movieDescription.text = movieResult.overview
        val baseURL = "https://image.tmdb.org/t/p/w500"
        val imageUrl = baseURL + movieResult.backdrop_path
        Glide.with(this)
            .load(imageUrl)
            .into(binding.detailImage)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView?.visibility = View.VISIBLE
        _binding = null
    }
}