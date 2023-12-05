package com.alqo.ui.detection

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.alqo.R
import com.alqo.databinding.FragmentDetectionBinding

class DetectionFragment : Fragment(R.layout.fragment_detection) {
    private lateinit var binding: FragmentDetectionBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetectionBinding.bind(view)

        dialog()
    }

    private fun dialog() {
    }
}