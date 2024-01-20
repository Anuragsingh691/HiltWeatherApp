package com.example.weatherapp.domain

object Util {

    fun kelvinToCelsius(kelvin: Double): Double {
        return kelvin - 273.15
    }

    fun formatTemperature(temperature: Double): String {
        return String.format("%.1f", temperature)
    }
}