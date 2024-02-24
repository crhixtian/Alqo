package com.alqo.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.alqo.EmptyActivity
import com.alqo.R
import com.alqo.databinding.FragmentProfileBinding
import com.alqo.repository.UserRepositoryImp
import com.alqo.utils.User.uid
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var binding: FragmentProfileBinding

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        recoverProfile()
        action()
    }

    private fun action() {
        binding.setting.setOnClickListener {
            startActivity(
                Intent(
                    context,
                    EmptyActivity::class.java
                )
            )
        }
    }

    private fun loadImage(
        imageURL: String,
        imageView: ShapeableImageView,
    ) {
        Picasso.get().load(imageURL).into(imageView)
    }

    private fun recoverProfile() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val user = UserRepositoryImp().obtainUser(uid())
                withContext(Dispatchers.Main) {
                    user?.let {
                        binding.names.text = it.names
                        binding.dni.text = "@" + it.dni
                        loadImage(it.image, binding.profilePhoto)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}