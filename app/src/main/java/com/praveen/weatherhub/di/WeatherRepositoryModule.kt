package com.praveen.weatherhub.di

import com.praveen.weatherhub.repo.WeatherRepository
import com.praveen.weatherhub.repo.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module


@Module
abstract class WeatherRepositoryModule {
    @Binds
    abstract fun bindsWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository
}