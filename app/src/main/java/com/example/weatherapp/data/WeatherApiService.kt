package com.example.weatherapp.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q") city: String,
        @Query("APPID") apiKey: String
    ): Response<WeatherResponse>

    @GET("forecast")
    suspend fun getWeatherForecast(
        @Query("q") city: String,
        @Query("APPID") apiKey: String
    ): Response<CityForecastResponse>
}