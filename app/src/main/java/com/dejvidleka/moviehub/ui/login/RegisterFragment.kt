package com.dejvidleka.moviehub.ui.login

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dejvidleka.moviehub.R
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

        val buttonSubmit = binding.buttonRegister
        buttonSubmit.setOnClickListener {
            val name = binding.firstnameInputEditText.text.toString()
            val lastName = binding.lastnameInputEditText.text.toString()
            val age = binding.ageInputEditText.text.toString()
            val password = binding.passwordInputEditText.text.toString()
            val email = binding.emailInputEditText.text.toString()
            navigate()
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

    private fun navigate() {
        val text = getString(R.string.already_have_an_acount)
        val spannableString = SpannableString(text)
        val clickableText = "Log in"
        val clickableTextStart = text.indexOf(clickableText)
        val clickableTextEnd = clickableTextStart + clickableText.length

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.container, LogInFragment())?.commit()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }

        spannableString.setSpan(
            clickableSpan,
            clickableTextStart,
            clickableTextEnd,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.alreadyHaveAccountText.text = spannableString
        binding.alreadyHaveAccountText.movementMethod = LinkMovementMethod.getInstance()

    }

}