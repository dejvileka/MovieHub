package com.dejvidleka.moviehub.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dejvidleka.moviehub.R
import com.dejvidleka.moviehub.databinding.FragmentRegisterBinding
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    companion object {
        fun newInstance() = RegisterFragment()
    }

    private lateinit var binding: FragmentRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentRegisterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val db = FirebaseFirestore.getInstance()
        val user = hashMapOf(
            "first" to "Ada",
            "last" to "Lovelace",
            "born" to 1815
        )
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d("added in db", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("not added in db", "Error adding document", e)
            }

        val buttonSubmit = binding.logInButton
        buttonSubmit.setOnClickListener {
            val name = binding.firstnameInputEditText.text.toString()
            val lastName = binding.lastnameInputEditText.text.toString()
            val age= binding.ageInputEditText.text.toString()
            val password= binding.passwordInputEditText.text.toString()

            val user = hashMapOf(
                "name" to name,
                "last_name" to lastName,
                "password" to password,
                "age" to age
            )

            db.collection("users")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    Log.d("added in db", "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w("not added in db", "Error adding document", e)
                }
        }
    }




}