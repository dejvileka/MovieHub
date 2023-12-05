package com.dejvidleka.moviehub.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dejvidleka.moviehub.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentRegisterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val db = FirebaseFirestore.getInstance()

        val buttonSubmit = binding.logInButton
        buttonSubmit.setOnClickListener {
            val name = binding.firstnameInputEditText.text.toString()
            val lastName = binding.lastnameInputEditText.text.toString()
            val age = binding.ageInputEditText.text.toString()
            val password = binding.passwordInputEditText.text.toString()
            val email = binding.emailInputEditText.text.toString()

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {

                        val firebaseUser = it.result!!.user!!
                        val userId = firebaseUser.uid
                        val userMap = hashMapOf(
                            "name" to name,
                            "last_name" to lastName,
                            "age" to age,
                        )

                        db.collection("users").document(userId).set(userMap)
                            .addOnSuccessListener { documentReference ->
                            }
                            .addOnFailureListener { e ->
                            }
                    }
                }
        }
    }




}