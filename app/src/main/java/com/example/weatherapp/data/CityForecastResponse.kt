package com.example.weatherapp.data

data class CityForecastResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<ForecastItems>,
    val message: Int
)