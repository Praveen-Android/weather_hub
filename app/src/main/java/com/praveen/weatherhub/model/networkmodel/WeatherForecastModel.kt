package com.praveen.weatherhub.model.networkmodel

import com.google.gson.annotations.SerializedName


data class WeatherForecastResponse(
        @field:SerializedName("data")
        val data: List<WeatherForecastItem?>? = null
)

data class WeatherForecastItem(

        @field:SerializedName("pres")
        val pres: Double? = null,

        @field:SerializedName("wind_cdir")
        val windCdir: String? = null,

        @field:SerializedName("clouds")
        val clouds: Double? = null,

        @field:SerializedName("wind_spd")
        val windSpd: Double? = null,

        @field:SerializedName("datetime")
        val datetime: String? = null,

        @field:SerializedName("sunrise_ts")
        val sunriseTs: Int? = null,

        @field:SerializedName("sunset_ts")
        val sunsetTs: Int? = null,

        @field:SerializedName("min_temp")
        val minTemp: Double? = null,

        @field:SerializedName("weather")
        val weather: WeatherForecast? = null,

        @field:SerializedName("max_temp")
        val maxTemp: Double? = null,

        @field:SerializedName("vis")
        val vis: Double? = null,

        @field:SerializedName("temp")
        val temp: Double? = null,

        @field:SerializedName("wind_dir")
        val windDir: Int? = null,

        @field:SerializedName("clouds_low")
        val cloudsLow: Int? = null,

        @field:SerializedName("rh")
        val rh: Double? = null,

        @field:SerializedName("ts")
        val ts: Long? = null,

        @field:SerializedName("valid_date")
        val valid_date: String? = null
)

data class WeatherForecast(

        @field:SerializedName("code")
        val code: Int? = null,

        @field:SerializedName("icon")
        val icon: String? = null,

        @field:SerializedName("description")
        val description: String? = null
)