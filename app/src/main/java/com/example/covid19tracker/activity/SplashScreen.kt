package com.example.covid19tracker.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.covid19tracker.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            val startApp = Intent(this@SplashScreen, FirstOnBoardScreen::class.java)
            startActivity(startApp)
            finish()
        },1000)
    }
}