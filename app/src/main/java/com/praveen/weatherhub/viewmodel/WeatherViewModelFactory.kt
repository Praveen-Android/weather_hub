package com.praveen.weatherhub.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.praveen.weatherhub.repo.WeatherRepository
import javax.inject.Inject


class WeatherViewModelFactory @Inject constructor(private val weatherRepository: WeatherRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
         if (modelClass.isAssignableFrom(FetchWeatherViewModel::class.java)) {
                return FetchWeatherViewModel(weatherRepository) as T
        } else if (modelClass.isAssignableFrom(WeatherForecastViewModel::class.java)) {
             return WeatherForecastViewModel(weatherRepository) as T
         } else if (modelClass.isAssignableFrom(CurrentWeatherViewModel::class.java)) {
             return CurrentWeatherViewModel(weatherRepository) as T
         } else {
             throw IllegalArgumentException("Unknown ViewModel class")
         }
    }
}