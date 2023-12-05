package com.alqo

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.alqo.ui.auth.AuthActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private val delay: Long = 200
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        splashScreen()
    }

    private fun splashScreen() {
        object : CountDownTimer(delay, delay) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                if (FirebaseAuth.getInstance().currentUser != null) {
                    startActivity(
                        Intent(
                            this@MainActivity,
                            AlqoActivity::class.java
                        )
                    )
                } else {
                    startActivity(
                        Intent(
                            this@MainActivity,
                            AuthActivity::class.java
                        )
                    )
                }

                finish()
            }
        }.start()
    }
}