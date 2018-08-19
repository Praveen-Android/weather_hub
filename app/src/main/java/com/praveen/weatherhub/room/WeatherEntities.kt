package com.praveen.weatherhub.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = DbDescriptor.TABLE_CURRENT_WEATHER)
data class CurrentWeather(

        @PrimaryKey(autoGenerate = true)
        var id:Long? = null,

        @ColumnInfo(name = "cityName")
        var cityName:String?,

        @ColumnInfo(name = "time")
        var time: String?,

        @ColumnInfo(name = "weatherDescription")
        var weatherDescription: String?,

        @ColumnInfo(name = "weatherIcon")
        var weatherIcon: String?,

        @ColumnInfo(name = "currTemp")
        var currTemp: Double?,

        @ColumnInfo(name = "pressure")
        var pressure: Double?,

        @ColumnInfo(name = "windDetails")
        var windDetails: String?,

        @ColumnInfo(name = "visibility")
        var visibility: Double?,

        @ColumnInfo(name = "clouds")
        var clouds: Double?,

        @ColumnInfo(name = "humidity")
        var humidity: Double?,

        @ColumnInfo(name = "pod")
        var pod: String?,

        @ColumnInfo(name = "latitude")
        var latitude: String?,

        @ColumnInfo(name = "longitude")
        var longitude: String?

)


@Entity(tableName = DbDescriptor.TABLE_WEATHER_FORECAST)
data class WeatherForecast(

        @PrimaryKey(autoGenerate = true)
        var id:Long? = null,

        @ColumnInfo(name = "cityName")
        var cityName:String?,

        @ColumnInfo(name = "weatherDescription")
        var weatherDescription: String?,

        @ColumnInfo(name = "weatherIcon")
        var weatherIcon: String?,

        @ColumnInfo(name = "currTemp")
        var currTemp: Double?,

        @ColumnInfo(name = "maxTemp")
        var maxTemp:Double?,

        @ColumnInfo(name = "minTemp")
        var minTemp:Double?,

        @ColumnInfo(name = "pressure")
        var pressure: Double?,

        @ColumnInfo(name = "windDetails")
        var windDetails: String?,

        @ColumnInfo(name = "visibility")
        var visibility: Double?,

        @ColumnInfo(name = "clouds")
        var clouds: Double?,

        @ColumnInfo(name = "humidity")
        var humidity: Double?,

        @ColumnInfo(name = "validDate")
        var validDate: String?

)