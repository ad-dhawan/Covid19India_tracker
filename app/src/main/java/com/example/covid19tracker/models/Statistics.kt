package com.example.covid19tracker.models

data class Statistics(
    val state: String,
    val totalCases: String,
    val deaths: String,
    val activeCases: String,
    val recovered: String
)