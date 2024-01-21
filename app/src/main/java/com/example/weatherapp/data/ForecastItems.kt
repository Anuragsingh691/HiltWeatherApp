package com.example.weatherapp.data

data class ForecastItems(
    val dt: Int,
    val dt_txt: String,
    val main: Main,
    val visibility: Int
)