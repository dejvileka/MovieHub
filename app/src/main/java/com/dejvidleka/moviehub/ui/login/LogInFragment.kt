package com.dejvidleka.moviehub.ui.login

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dejvidleka.moviehub.databinding.FragmentLogInBinding
import com.dejvidleka.moviehub.ui.MainActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

class LogInFragment : Fragment() {

    private lateinit var binding: FragmentLogInBinding
    companion object {
        fun newInstance() = LogInFragment()
    }

    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
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
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val user = FirebaseAuth.getInstance().currentUser
                        Toast.makeText(this.requireContext(), "${user?.email}", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(
                            this.requireContext(), "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}

private fun guestMode() {

}

