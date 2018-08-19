package com.praveen.weatherhub.repo

import com.praveen.weatherhub.model.locationmodel.LocationResponse
import com.praveen.weatherhub.model.networkmodel.WeatherDetails
import io.reactivex.Single


interface WeatherRepository {

    //API operations

    fun getCurrentWeather(cityName: String): Single<WeatherDetails>

    fun getCurrentWeather(latitude: String, longitude:String): Single<WeatherDetails>

    fun processCurrentLocation(latitude: Double, longitude:Double): Single<LocationResponse>

    fun getWeatherForecast(cityName: String) : Single<List<WeatherDetails>>

    fun getWeatherForecast(latitude: String, longitude:String): Single<List<WeatherDetails>>

    fun getCurrentLocation() : String

    fun setCurrentLocation(cityName: String)

    fun getCurrentLatitude(): String

    fun setCurrentLatitude(latitude: String)

    fun getCurrentLongitude(): String

    fun setCurrentLongitude(longitude: String)

    //DB operations

    fun saveCurrentWeatherToDb(weatherDetail: WeatherDetails)

    fun getCurrentWeatherFromDb(cityName: String) : Single<WeatherDetails>

    fun saveWeatherForecastToDb(weatherDetailsList: List<WeatherDetails>)

    fun getWeatherForecastFromDb(cityName: String) : Single<List<WeatherDetails>>

    //Job Scheduler operation

    fun scheduleWeatherJob(): Single<WeatherDetails>?

}