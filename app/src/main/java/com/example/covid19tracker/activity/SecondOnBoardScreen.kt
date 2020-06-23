package com.example.covid19tracker.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.covid19tracker.R

class SecondOnBoardScreen : AppCompatActivity() {

    lateinit var btnNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_on_board_screen)

        btnNext = findViewById(R.id.btnNext)
        btnNext.setOnClickListener {
            val startNext = Intent(this, ThirdOnBoardScreen::class.java)
            startActivity(startNext)
            finish()
        }
    }
}