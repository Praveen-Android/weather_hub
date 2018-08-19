package com.praveen.weatherhub.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Single

@Dao
interface WeatherHubDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weatherDetails: CurrentWeather)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(weatherDetails: List<WeatherForecast>)

    @Query("SELECT * FROM currentweather WHERE cityName LIKE :city LIMIT 1")
    fun getCurrentWeather(city:String): Single<CurrentWeather>

    @Query("SELECT * FROM  weatherforecast WHERE cityName LIKE :city")
    fun getWeatherForecast(city:String): Single<List<WeatherForecast>>
}