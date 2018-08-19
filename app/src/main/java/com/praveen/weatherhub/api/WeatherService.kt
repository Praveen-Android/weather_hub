package com.praveen.weatherhub.api

import com.praveen.weatherhub.model.networkmodel.WeatherForecastResponse
import com.praveen.weatherhub.model.networkmodel.WeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WeatherService {

    @GET("current")
    @Headers("Content-type: application/json")
    fun requestCurrentWeather(
            @Query("lat") latitude: String,
            @Query("lon") longitude: String,
            @Query("key") key:String
    ): Single<WeatherResponse>


    @GET("current")
    @Headers("Content-type: application/json")
    fun requestCurrentWeather(
            @Query("city") cityName: String,
            @Query("key") key:String
    ): Single<WeatherResponse>


    @GET("forecast/daily")
    @Headers("Content-type: application/json")
    fun requestWeatherForecast(
            @Query("city") cityName: String,
            @Query("key") key:String
    ): Single<WeatherForecastResponse>


    @GET("forecast/daily")
    @Headers("Content-type: application/json")
    fun requestWeatherForecast(
            @Query("lat") latitude: String,
            @Query("lon") longitude: String,
            @Query("key") key:String
    ): Single<WeatherForecastResponse>
}