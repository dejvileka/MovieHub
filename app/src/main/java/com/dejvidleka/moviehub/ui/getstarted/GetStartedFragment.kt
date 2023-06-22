package com.dejvidleka.moviehub.ui.getstarted

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dejvidleka.moviehub.R

class GetStartedFragment : Fragment() {

    companion object {
        fun newInstance() = GetStartedFragment()
    }

    private lateinit var viewModel: GetStartedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_get_started, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GetStartedViewModel::class.java)
        // TODO: Use the ViewModel
    }

}