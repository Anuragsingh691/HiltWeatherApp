package com.example.weatherapp.domain

import android.view.View
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

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

    fun getWeekdayNameFromDate(dateString: String, dateFormat: String = "yyyy-MM-dd"): String {
        val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.getDefault())
        try {
            val date = simpleDateFormat.parse(dateString)
            val calendar = Calendar.getInstance()
            calendar.time = date ?: Date()
            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            val weekdays = arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
            return weekdays[dayOfWeek - 1]

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return "Invalid Date"
    }
}