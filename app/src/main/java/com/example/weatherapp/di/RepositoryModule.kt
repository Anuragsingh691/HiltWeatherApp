package com.example.weatherapp.di

import com.example.weatherapp.data.WeatherRepo
import com.example.weatherapp.data.WeatherRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMyRepository(
        weatherRepoImpl: WeatherRepoImpl
    ): WeatherRepo
}