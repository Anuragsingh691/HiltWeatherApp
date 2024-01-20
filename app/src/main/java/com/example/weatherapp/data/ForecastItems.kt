package com.example.weatherapp.data

data class ForecastItems(
    val clouds: Clouds,
    val dt: Int,
    val dt_txt: String,
    val main: Main,
    val pop: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)