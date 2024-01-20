package com.example.weatherapp.view

sealed class WeatherViewState<out T : Any> {
    data class Success<T : Any>(val data: T) :
        WeatherViewState<T>()

    data class Failure(val message: String) : WeatherViewState<Nothing>()
    object Loading : WeatherViewState<Nothing>()
}