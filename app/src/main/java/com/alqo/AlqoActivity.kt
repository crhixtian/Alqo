package com.alqo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.alqo.databinding.ActivityAlqoBinding
import com.alqo.ui.notice.CreateFragment
import com.alqo.ui.notice.DetectionFragment
import com.alqo.ui.notice.HomeFragment
import com.alqo.ui.notice.ProfileFragment

class AlqoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlqoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlqoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigation()
        replaceFragment(HomeFragment())
    }

    private fun navigation() {
        binding.navigationAlqo.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.iconHome -> replaceFragment(HomeFragment())
                R.id.iconProfile -> replaceFragment(ProfileFragment())
                R.id.iconCreate -> replaceFragment(CreateFragment())
                R.id.iconDetection -> replaceFragment(DetectionFragment())
                else -> {
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager.beginTransaction()
        fragmentManager.replace(
            R.id.frameAlqo,
            fragment
        )
        fragmentManager.commit()
    }
}