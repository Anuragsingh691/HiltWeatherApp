package com.example.weatherapp.data

interface WeatherRepo {
    suspend fun getCurrentWeather(city: String): WeatherResponse

    suspend fun getWeatherForecast(city: String): CityForecastResponse
}