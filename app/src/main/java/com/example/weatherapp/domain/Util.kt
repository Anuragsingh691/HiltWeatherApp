package com.example.weatherapp.domain

import android.view.View

object Util {

    fun kelvinToCelsius(kelvin: Double): Double {
        return kelvin - 273.15
    }

    fun formatTemperature(temperature: Double): String {
        return String.format("%.1f", temperature)
    }

    fun View.show() {
        visibility = View.VISIBLE
    }

    fun View.hide() {
        visibility = View.GONE
    }
}