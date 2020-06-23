package com.example.covid19tracker.models

data class Countries (
    val country: String,
    val newCases: Int,
    val cases: Int,
    val newDeaths: Int,
    val deaths: Int,
    val newRecovered: Int,
    val recovered: Int
)