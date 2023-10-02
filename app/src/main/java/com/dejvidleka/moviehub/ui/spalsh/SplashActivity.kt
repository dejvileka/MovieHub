package com.dejvidleka.moviehub.ui.spalsh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dejvidleka.moviehub.databinding.ActivitySplashBinding
import com.dejvidleka.moviehub.ui.MainActivity
import com.dejvidleka.moviehub.utils.SPLASH_DELAY


import android.content.Intent

import android.os.Handler
import android.view.WindowManager
import dagger.hilt.android.AndroidEntryPoint

class SplashActivity : AppCompatActivity(){

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        navigateToMainScreen()
    }

    private fun navigateToMainScreen() {
        Handler().postDelayed({
            val nextScreenIntent = Intent(this, MainActivity::class.java)
            startActivity(nextScreenIntent)
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }, SPLASH_DELAY.toLong())
    }
}
