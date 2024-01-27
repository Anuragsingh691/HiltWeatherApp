package com.example.weatherapp.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ExampleVM(
    val someData:Int
) : ViewModel() {
    val dat = MutableLiveData<Int>()

    init {
        val randomNum = java.util.Random().nextInt(6)
        dat.value = randomNum
    }
}