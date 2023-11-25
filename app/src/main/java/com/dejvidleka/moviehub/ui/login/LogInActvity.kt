package com.dejvidleka.moviehub.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.dejvidleka.moviehub.databinding.LogInActvityBinding

class LogInActvity : AppCompatActivity() {
private lateinit var binding: LogInActvityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= LogInActvityBinding.inflate(LayoutInflater.from(parent))
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, LogInFragment.newInstance())
                .commitNow()
        }
    }
}