package com.example.zalmanach

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.zalmanach.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.pbLadestatusSplash.max = 100

        object: CountDownTimer(3500,30) {
            override fun onTick(millisUntilFinished: Long) {
                val progress = ((3500 - millisUntilFinished) / 30).toInt()
                binding.pbLadestatusSplash.progress = progress
            }

            override fun onFinish() {
                startActivity(Intent(this@SplashActivity,LoginActivity::class.java))    // Hier ändern der Activities möglich
                finish()
            }
        }.start()
    }
}