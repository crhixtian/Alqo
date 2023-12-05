package com.alqo.ui.notice

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.alqo.R
import com.alqo.databinding.FragmentSettingBinding
import com.alqo.ui.auth.AuthActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class SettingFragment : Fragment(R.layout.fragment_setting) {
    private lateinit var binding: FragmentSettingBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingBinding.bind(view)
        action()
    }

    private fun action() {
        binding.logout.setOnClickListener {
            logOut()
        }
    }

    private fun logOut() {
        FirebaseAuth.getInstance().signOut()
        GoogleSignIn.getClient(requireContext(), GoogleSignInOptions.DEFAULT_SIGN_IN)
            .signOut()
            .addOnCompleteListener {
                startActivity(
                    Intent(
                        context,
                        AuthActivity::class.java
                    )
                )
            }
    }
}