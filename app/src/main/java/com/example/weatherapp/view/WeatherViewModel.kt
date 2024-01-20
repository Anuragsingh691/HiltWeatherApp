package com.example.weatherapp.view

import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.WeatherRepoImpl
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(repo: Lazy<WeatherRepoImpl>) : ViewModel() {

    init {
        repo.get()
    }
}