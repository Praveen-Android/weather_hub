package com.praveen.weatherhub.api

import com.praveen.weatherhub.model.locationmodel.LocationResponse
import com.praveen.weatherhub.model.networkmodel.WeatherForecastResponse
import com.praveen.weatherhub.model.networkmodel.WeatherResponse
import io.reactivex.Single
import javax.inject.Inject


class WeatherDataSource @Inject constructor(private val weatherService: WeatherService, private val geocodingService: GeocodingService) {

    fun requestCityAddressByName(cityName: String): Single<LocationResponse> = geocodingService.requestCityAddressByName(cityName)

    fun requestCityAddressByLatLng(latLng: String): Single<LocationResponse> = geocodingService.requestCityAddressByLatLng(latLng)

    fun requestCurrentWeather(latitude: String, longitude: String): Single<WeatherResponse> =
            weatherService.requestCurrentWeather(latitude, longitude, ApiEndPoints.API_KEY)

    fun requestCurrentWeather(cityName: String): Single<WeatherResponse> =
            weatherService.requestCurrentWeather(cityName, ApiEndPoints.API_KEY)

    fun requestWeatherForecast(cityName: String): Single<WeatherForecastResponse> =
            weatherService.requestWeatherForecast(cityName, ApiEndPoints.API_KEY)

    fun requestWeatherForecast(latitude: String, longitude: String): Single<WeatherForecastResponse> =
            weatherService.requestWeatherForecast(latitude, longitude, ApiEndPoints.API_KEY)
}