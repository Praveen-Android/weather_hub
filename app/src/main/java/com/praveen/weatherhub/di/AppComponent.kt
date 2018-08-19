package com.praveen.weatherhub.di

import com.praveen.weatherhub.scheduler.WeatherJobService
import com.praveen.weatherhub.ui.CurrentWeatherActivity
import com.praveen.weatherhub.ui.FetchWeatherActivity
import com.praveen.weatherhub.ui.WeatherForecastActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, NetworkModule::class, RoomModule::class, WeatherRepositoryModule::class))
interface AppComponent {

    fun inject(weatherActivity: FetchWeatherActivity)

    fun inject(currentWeatherActivity: CurrentWeatherActivity)

    fun inject(weatherForecastActivity: WeatherForecastActivity)

    fun inject(weatherJobService: WeatherJobService)

}