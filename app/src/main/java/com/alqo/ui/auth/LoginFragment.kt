package com.alqo.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alqo.AlqoActivity
import com.alqo.R
import com.alqo.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        action()
    }

    private fun action() {
        binding.textRegister.setOnClickListener {
            findNavController()
                .navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.buttonLogin.setOnClickListener {
            signInWithEmailAndPass()
        }
    }

    private fun signInWithEmailAndPass() {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(
            binding.email.text.toString(),
            binding.password.text.toString()
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                startActivity(
                    Intent(
                        context,
                        AlqoActivity::class.java
                    )
                )
            } else {
                showMessage(binding.email.toString())
                showMessage("Credentials incorrect")
            }
        }
    }

    private fun showMessage(mensaje: String) {
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
    }
}