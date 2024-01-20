package com.example.weatherapp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.domain.Constants
import com.example.weatherapp.domain.Util.formatTemperature
import com.example.weatherapp.domain.Util.hide
import com.example.weatherapp.domain.Util.kelvinToCelsius
import com.example.weatherapp.domain.Util.show
import com.example.weatherapp.view.WeatherViewModel
import com.example.weatherapp.view.WeatherViewState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeWeather()
    }

    override fun onResume() {
        super.onResume()
        fetchWeatherData()
    }

    private fun observeWeather() {
        viewModel.weatherLiveData.observe(this) { weatherViewState ->
            when (weatherViewState) {
                is WeatherViewState.Loading -> {
                    startLoader()
                }

                is WeatherViewState.Success -> {
                    cancelLoader()
                    val weatherResponse = weatherViewState.data
                    val celsiusTemperature =
                        formatTemperature(kelvinToCelsius(weatherResponse.main.temp))
                    val temperatureText = getString(
                        R.string.temperature_format,
                        celsiusTemperature,
                        getString(R.string.unit_celsius)
                    )
                    binding.currentTemp.text = temperatureText
                    binding.currentCity.text = Constants.CITY
                }

                is WeatherViewState.Failure -> {
                    cancelLoader()
                    showSnackBarWithRetry()
                }
            }
        }
    }

    private fun startLoader() {
        binding.loader.show()
        binding.loader.playAnimation()
    }

    private fun cancelLoader() {
        binding.loader.hide()
        binding.loader.cancelAnimation()
    }

    private fun showSnackBarWithRetry() {
        val rootView: View = findViewById(android.R.id.content)

        val snackBar = Snackbar.make(
            rootView,
            "Something went wrong",
            Snackbar.LENGTH_INDEFINITE
        )

        snackBar.setActionTextColor(resources.getColor(R.color.error_bg))
        snackBar.setAction("Retry") {
            fetchWeatherData()
            snackBar.dismiss()
        }

        snackBar.show()
    }

    private fun fetchWeatherData() {
        viewModel.getCurrentCityWeather(Constants.CITY)
    }
}