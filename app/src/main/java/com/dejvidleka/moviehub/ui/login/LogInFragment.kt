package com.dejvidleka.moviehub.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dejvidleka.moviehub.R
import com.dejvidleka.moviehub.databinding.FragmentLogInBinding
import com.dejvidleka.moviehub.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth

class LogInFragment : Fragment() {

    private lateinit var binding: FragmentLogInBinding
    private lateinit var viewModel: AuthViewModel

    companion object {
        fun newInstance() = LogInFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        navigate()
    }

    private fun setupListeners() {
        binding.guestButton.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }

        binding.logInButton.setOnClickListener {
            val email = binding.usernameInputEditText.text.toString().trim()
            val password = binding.passwordInputEditText.text.toString().trim()

            if (validateForm(email, password)) {
                logInUser(email, password)
            }
        }
    }

    private fun validateForm(email: String?, password: String?): Boolean {
        return email != null && password != null
    }

    private fun logInUser(email: String?, password: String?) {
        if (email != null && password != null) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = FirebaseAuth.getInstance().currentUser
                        Toast.makeText(context, "${user?.email}", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun navigate() {
        val fullText = getString(R.string.new_to_platform)
        val spannableString = SpannableString(fullText)

        val clickablePart = "Create an account"
        val clickablePartStart = fullText.indexOf(clickablePart)
        val clickablePartEnd = clickablePartStart + clickablePart.length

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.container, RegisterFragment())?.commit()
            }

            @SuppressLint("PrivateResource")
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }

        spannableString.setSpan(
            clickableSpan,
            clickablePartStart,
            clickablePartEnd,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.createAcountText.text = spannableString
        binding.createAcountText.movementMethod = LinkMovementMethod.getInstance()
    }
}
