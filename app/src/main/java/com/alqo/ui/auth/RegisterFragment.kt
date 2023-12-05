package com.alqo.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alqo.R
import com.alqo.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var binding: FragmentRegisterBinding

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)

        action()
    }

    private fun action() {
        binding.textLogIn.setOnClickListener {
            findNavController()
                .navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.btnRegister.setOnClickListener {
            checkDniExistence(binding.dniRegister.text.toString()) { dniExists ->
                if (dniExists) {
                    showMessage("DNI exists")
                } else {
                    registerUser()
                }
            }
        }
    }

    private fun checkDniExistence(dni: String, callback: (Boolean) -> Unit) {
        FirebaseFirestore.getInstance()
            .collection("usuario")
            .whereEqualTo("dni", dni)
            .get()
            .addOnSuccessListener { documents ->
                callback(!documents.isEmpty)
            }
            .addOnFailureListener {
                showMessage("Error: ${it.message}")
                callback(false)
            }
    }

    private fun registerUser() {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
            binding.emailRegister.text.toString(),
            binding.confirmPasswordRegister.text.toString()
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                saveInfoUser()
            } else {
                showMessage("${it.exception?.message}")
            }
        }
    }

    private fun saveInfoUser() {
        FirebaseFirestore.getInstance()
            .collection("usuario")
            .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
            .set(
                hashMapOf(
                    "nombres" to binding.fullNameRegister.text.toString(),
                    "dni" to binding.dniRegister.text.toString(),
                    "type" to "user"
                )
            ).addOnSuccessListener {
                findNavController()
                    .navigate(R.id.action_registerFragment_to_loginFragment)
                sendEmailVerify()
            }.addOnFailureListener {
                showMessage("${it.message}")
            }
    }

    private fun sendEmailVerify() {
        FirebaseAuth.getInstance().currentUser!!
            .sendEmailVerification()
    }

    private fun showMessage(mensaje: String) {
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
    }
}