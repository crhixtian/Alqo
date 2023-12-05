package com.alqo.ui.notice

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.alqo.R
import com.alqo.databinding.FragmentCreateBinding
import com.alqo.utils.User.uid
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class CreateFragment : Fragment(R.layout.fragment_create) {
    private lateinit var binding: FragmentCreateBinding
    private val imagePicker =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                result.data?.data?.let {
                    uploadImage(it)
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateBinding.bind(view)
        action()
        user()
    }

    private fun action() {
        binding.uploadImage.setOnClickListener {
            openGallery()
        }
    }

    private fun user() {
        FirebaseFirestore.getInstance()
            .collection("usuario")
            .document(uid())
            .get()
            .addOnSuccessListener {
                binding.username.text = it.getString("nombres").toString()
            }
    }

    private fun openGallery() {
        val intent =
            Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        imagePicker.launch(intent)
    }

    private fun savePost(imageUrl: String) {
        binding.buttonSave.setOnClickListener {
            FirebaseFirestore.getInstance().collection("post")
                .add(
                    hashMapOf(
                        "nombres" to binding.username.text.toString(),
                        "create" to Timestamp.now(),
                        "content" to binding.content.text.toString(),
                        "image" to imageUrl,
                        "uid" to uid()
                    )
                ).addOnSuccessListener {
                    replaceFragment(HomeFragment())
                }
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = parentFragmentManager.beginTransaction()
        fragmentManager.replace(
            R.id.frameAlqo,
            fragment
        )
        fragmentManager.commit()
    }

    private fun uploadImage(uri: Uri) {
        val seconds = System.currentTimeMillis()
        val pathImg = "${uid()}/post/PsT${seconds}.png"
        val bitmap =
            BitmapFactory.decodeStream(
                requireContext().contentResolver.openInputStream(uri),
            )
        visibility()
        binding.image.setImageBitmap(bitmap)
        FirebaseStorage.getInstance()
            .reference
            .child(pathImg)
            .putFile(uri)
            .addOnSuccessListener {
                FirebaseStorage.getInstance()
                    .reference
                    .child(pathImg)
                    .downloadUrl
                    .addOnSuccessListener {
                        savePost(it.toString())
                    }
            }
    }

    private fun visibility() {
        binding.image.visibility = View.VISIBLE
    }
}