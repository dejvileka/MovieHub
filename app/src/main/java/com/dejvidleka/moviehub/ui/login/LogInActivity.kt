package com.dejvidleka.moviehub.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dejvidleka.moviehub.R
import com.dejvidleka.moviehub.databinding.LogInActvityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogInActivity : AppCompatActivity() {
private lateinit var binding: LogInActvityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= LogInActvityBinding.inflate(layoutInflater)
        setContentView(binding.root)
            supportFragmentManager.beginTransaction().replace(R.id.container, LogInFragment.newInstance()).commit()


    }
}