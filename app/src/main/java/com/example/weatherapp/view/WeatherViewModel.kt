package com.example.weatherapp.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.ApiException
import com.example.weatherapp.data.ForecastResponse
import com.example.weatherapp.data.WeatherRepoImpl
import com.example.weatherapp.data.WeatherResponse
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repo: Lazy<WeatherRepoImpl>) : ViewModel() {

    private val _weatherLiveData = MutableLiveData<WeatherViewState<WeatherResponse>>()
    val weatherLiveData: LiveData<WeatherViewState<WeatherResponse>> = _weatherLiveData

    private val _forecastLiveData = MutableLiveData<WeatherViewState<ForecastResponse>>()
    val forecastLiveData: LiveData<WeatherViewState<ForecastResponse>> = _forecastLiveData

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleGenericException(throwable)
    }

    fun getWeatherForecast(city: String) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            _weatherLiveData.postValue(WeatherViewState.Loading)
            _forecastLiveData.postValue(WeatherViewState.Loading)

            try {
                val deferredResult1 = async { repo.get().getCurrentWeather(city) }
                val deferredResult2 = async { repo.get().getWeatherForecast(city) }

                // Wait for both API calls to complete
                val result1 = deferredResult1.await()
                val result2 = deferredResult2.await()
                _weatherLiveData.postValue(WeatherViewState.Success(result1))
                _forecastLiveData.postValue(WeatherViewState.Success(result2))
            } catch (apiException: ApiException) {
                handleApiException(apiException)
            } catch (e: Exception) {
                handleGenericException(e)
            }
        }
    }

    private fun handleApiException(apiException: ApiException) {
        _weatherLiveData.postValue(
            WeatherViewState.Failure(
                apiException.message ?: "Unknown error"
            )
        )
        _forecastLiveData.postValue(
            WeatherViewState.Failure(
                apiException.message ?: "Unknown error"
            )
        )
    }

    private fun handleGenericException(throwable: Throwable) {
        _weatherLiveData.postValue(WeatherViewState.Failure(throwable.message.toString()))
        _forecastLiveData.postValue(WeatherViewState.Failure(throwable.message.toString()))
    }
}