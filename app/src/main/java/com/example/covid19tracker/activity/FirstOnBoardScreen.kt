package com.example.covid19tracker.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.covid19tracker.R

class FirstOnBoardScreen : AppCompatActivity() {

    lateinit var btnNext: Button
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences(getString(R.string.preference_file_name),Context.MODE_PRIVATE)

        val isNew = sharedPreferences.getBoolean("isNew", true)
        if (!isNew){
            val skipActivity = Intent(this, MainActivity::class.java)
            startActivity(skipActivity)
        } else {
            setContentView(R.layout.activity_first_onboard_screen)
        }

        setContentView(R.layout.activity_first_onboard_screen)

        btnNext = findViewById(R.id.btnNext)
        btnNext.setOnClickListener {
            val startNext = Intent(this, SecondOnBoardScreen::class.java)
            startActivity(startNext)
            sharedPreferences.edit().putBoolean("isNew", false).apply()
            finish()
        }
    }
}