package com.example.weatherapp.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyViewModelFactory(private val someDependency: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExampleVM::class.java)) {
            return ExampleVM(someDependency) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
