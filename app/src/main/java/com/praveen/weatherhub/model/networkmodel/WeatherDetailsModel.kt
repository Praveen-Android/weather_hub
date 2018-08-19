package com.praveen.weatherhub.model.networkmodel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherDetails(
        val cityName:String? = null,
        val time: String? = null,
        val weatherDescription: String? = null,
        val weatherIcon: String? = null,
        val pod: String? = null,
        val currTemp: Double? = 0.0,
        val maxTemp:Double? = 0.0,
        val minTemp:Double? = 0.0,
        val pressure: Double? = 0.0,
        val windDetails: String?,
        val visibility: Double? = 0.0,
        val clouds: Double? = 0.0,
        val humidity: Double? = 0.0,
        val validDate: String? = null,
        val lat: String? = null,
        val lon: String? = null

):Parcelable


