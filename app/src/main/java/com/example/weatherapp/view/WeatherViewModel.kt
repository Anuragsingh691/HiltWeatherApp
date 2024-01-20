package com.example.weatherapp.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.ApiException
import com.example.weatherapp.data.WeatherRepoImpl
import com.example.weatherapp.data.WeatherResponse
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repo: Lazy<WeatherRepoImpl>) : ViewModel() {

    private val _weatherLiveData = MutableLiveData<WeatherViewState<WeatherResponse>>()
    val weatherLiveData: LiveData<WeatherViewState<WeatherResponse>> = _weatherLiveData

    fun getCurrentCityWeather(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _weatherLiveData.postValue(WeatherViewState.Loading)

            try {
                val weatherResponse = repo.get().getCurrentWeather(city)
                _weatherLiveData.postValue(WeatherViewState.Success(weatherResponse))
            } catch (apiException: ApiException) {
                _weatherLiveData.postValue(
                    WeatherViewState.Failure(
                        apiException.message ?: "Unknown error"
                    )
                )
            } catch (e: Exception) {
                _weatherLiveData.postValue(WeatherViewState.Failure("Network error"))
            }
        }
    }
}