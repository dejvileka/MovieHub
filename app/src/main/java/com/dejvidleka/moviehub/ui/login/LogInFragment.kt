package com.dejvidleka.moviehub.ui.login

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dejvidleka.moviehub.databinding.FragmentLogInBinding
import com.dejvidleka.moviehub.ui.MainActivity

class LogInFragment : Fragment() {

    private lateinit var binding: FragmentLogInBinding
    companion object {
        fun newInstance() = LogInFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLogInBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        guestMode()
    }

    private fun guestMode() {
        binding.guestButton.setOnClickListener {
          val intent= Intent(context, MainActivity::class.java)
            startActivity(intent)
        }
    }

}