package com.example.weatherapp.data

import com.example.weatherapp.domain.Constants.API_KEY
import okio.IOException
import javax.inject.Inject

class WeatherRepoImpl @Inject constructor(
    private val weatherApiService: WeatherApiService
) : WeatherRepo {
    override suspend fun getCurrentWeather(city: String): WeatherResponse {
        return try {
            val response = weatherApiService.getCurrentWeather(city, API_KEY)
            if (response.isSuccessful) {
                response.body() ?: throw NullPointerException("Response body is null")
            } else {
                throw ApiException("Error fetching current weather: ${response.message()}")
            }
        } catch (e: Exception) {
            throw handleApiException(e)
        }
    }

    override suspend fun getWeatherForecast(city: String): CityForecastResponse {
        return try {
            val response = weatherApiService.getWeatherForecast(city, API_KEY)
            if (response.isSuccessful) {
                response.body() ?: throw NullPointerException("Response body is null")
            } else {
                throw ApiException("Error fetching forecast weather: ${response.message()}")
            }
        } catch (e: Exception) {
            throw handleApiException(e)
        }
    }

    private fun handleApiException(e: Exception): ApiException {
        return when (e) {
            is IOException -> ApiException("Network error: ${e.message}")
            else -> ApiException("An error occurred: ${e.message}")
        }
    }
}

class ApiException(message: String) : Exception(message)
