package com.alqo.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alqo.databinding.ActivityFullImageBinding
import com.squareup.picasso.Picasso

class FullImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFullImageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadImage(intent.getStringExtra("IMAGE_URL").toString())
    }

    private fun loadImage(
        imageURL: String
    ) {
        Picasso.get().load(imageURL).into(binding.imageViewFullscreen)
    }
}