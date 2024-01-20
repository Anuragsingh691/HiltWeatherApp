package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.weatherapp.domain.Constants
import com.example.weatherapp.view.WeatherViewModel
import com.example.weatherapp.view.WeatherViewState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeWeather()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCurrentCityWeather(Constants.CITY)
    }

    private fun observeWeather() {
        viewModel.weatherLiveData.observe(this, Observer { weatherViewState ->
            when (weatherViewState) {
                is WeatherViewState.Loading -> {
                    Toast.makeText(this, "loading", Toast.LENGTH_LONG).show()
                }

                is WeatherViewState.Success -> {
                    val weatherResponse = weatherViewState.data
                    Toast.makeText(this, "success ${weatherResponse.main.temp}", Toast.LENGTH_LONG)
                        .show()
                }

                is WeatherViewState.Failure -> {
                    val errorMessage = weatherViewState.message
                    Toast.makeText(this, "Api failed $errorMessage", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}