package com.praveen.weatherhub.utils

import com.praveen.weatherhub.model.networkmodel.WeatherDetails
import com.praveen.weatherhub.model.networkmodel.WeatherForecastItem
import com.praveen.weatherhub.model.networkmodel.WeatherResponse
import com.praveen.weatherhub.room.CurrentWeather
import com.praveen.weatherhub.room.WeatherForecast

object ModelUtils{

    /**
     * Parse the current weather api (http://api.weatherbit.io/v2.0/current) response to WeatherDetails
     * @see com.praveen.weatherhub.api.model.WeatherDetails
     * @see com.praveen.weatherhub.api.model.WeatherResponse
     */
    fun getCurrentWeatherDetails(weatherResponse: WeatherResponse) : WeatherDetails {
        val weatherItem = weatherResponse.data?.get(0)
        val cityName = weatherItem?.city_name.plus(",").plus(weatherItem?.state_code).plus(",").plus(weatherItem?.country_code)
        val time = weatherItem?.ob_time
        val weatherDescription = weatherItem?.weather?.description
        val weatherIcon = weatherItem?.weather?.code
        val temperature = weatherItem?.temp
        val cloudCoverPercentage: Double? = weatherItem?.clouds
        val pressure = weatherItem?.pres
        val windDetails = weatherItem?.wind_spd.toString().plus(" ").plus(weatherItem?.wind_cdir)
        val humidity = weatherItem?.rh
        val pod = weatherItem?.pod
        val visibility = weatherItem?.vis
        val lat = weatherItem?.lat.toString()
        val lon = weatherItem?.lon.toString()

        return WeatherDetails(cityName = cityName,
                time = time,
                weatherDescription = weatherDescription,
                weatherIcon = weatherIcon,
                currTemp = temperature,
                clouds = cloudCoverPercentage,
                pressure = pressure,
                windDetails = windDetails,
                humidity = humidity,
                visibility = visibility,
                pod = pod,
                lat = lat,
                lon = lon
        )
    }

    /**
     * Transform/convert WeatherDetails object to CurrentWeather
     * @see com.praveen.weatherhub.api.model.WeatherDetails
     * @see com.praveen.weatherhub.room.CurrentWeather
     *
     */
    fun getCurrentWeatherEntity(cityName: String, weatherDetails: WeatherDetails) : CurrentWeather {
        val time = weatherDetails.time
        val weatherDescription = weatherDetails.weatherDescription
        val weatherIcon = weatherDetails.weatherIcon
        val temperature = weatherDetails.currTemp
        val cloudCoverPercentage = weatherDetails.clouds
        val pressure = weatherDetails.pressure
        val windDetails = weatherDetails.windDetails
        val humidity = weatherDetails.humidity
        val visibility = weatherDetails.visibility
        val pod = weatherDetails.pod
        val lat = weatherDetails.lat.toString()
        val lon = weatherDetails.lon.toString()

        return CurrentWeather(cityName = cityName,
                time = time,
                weatherDescription = weatherDescription,
                weatherIcon = weatherIcon,
                currTemp = temperature,
                clouds = cloudCoverPercentage,
                pressure = pressure,
                windDetails = windDetails,
                humidity = humidity,
                visibility = visibility,
                pod = pod,
                latitude = lat,
                longitude = lon
                )
    }

    /**
     * Transform/convert CurrentWeather object to WeatherDetails
     * @see com.praveen.weatherhub.api.model.WeatherDetails
     * @see com.praveen.weatherhub.room.CurrentWeather
     *
     */
    fun getCurrentWeatherDetails(weatherItem: CurrentWeather): WeatherDetails {
        val cityName = weatherItem.cityName
        val time = weatherItem.time
        val weatherDescription = weatherItem.weatherDescription
        val weatherIcon = weatherItem.weatherIcon
        val temperature = weatherItem.currTemp
        val cloudCoverPercentage: Double? = weatherItem.clouds
        val pressure = weatherItem.pressure
        val windDetails = weatherItem.windDetails
        val humidity = weatherItem.humidity
        val pod = weatherItem.pod
        val visibility = weatherItem.visibility
        val lat = weatherItem.latitude
        val lon = weatherItem.longitude

        return WeatherDetails(cityName = cityName,
                time = time,
                weatherDescription = weatherDescription,
                weatherIcon = weatherIcon,
                currTemp = temperature,
                clouds = cloudCoverPercentage,
                pressure = pressure,
                windDetails = windDetails,
                humidity = humidity,
                visibility = visibility,
                pod = pod,
                lat = lat,
                lon = lon
        )
    }

    /**
     * Parse the weather forecast api (http://api.weatherbit.io/v2.0/forecast/daily) response item to WeatherDetails
     * @see com.praveen.weatherhub.api.model.WeatherDetails
     * @see com.praveen.weatherhub.api.model.forecast.WeatherItem
     */
    fun getWeatherForecastDetails(cityName: String, weatherItem: WeatherForecastItem) : WeatherDetails {
        val weatherDescription:String? = weatherItem.weather?.description
        val weatherIcon:Int? = weatherItem.weather?.code?.toInt()
        val temperature:Double? = weatherItem.temp
        val maxTemperature:Double? = weatherItem.maxTemp
        val minTemperature:Double? = weatherItem.minTemp
        val cloudCoverPercentage: Double? = weatherItem.clouds
        val windDetails = weatherItem.windSpd.toString().plus(weatherItem.windCdir)
        val humidity = weatherItem.rh
        val visibility = weatherItem.vis
        val date = weatherItem.valid_date

        return WeatherDetails(cityName = cityName,
                weatherDescription = weatherDescription,
                weatherIcon = weatherIcon.toString(),
                currTemp = temperature,
                maxTemp = maxTemperature,
                minTemp = minTemperature,
                clouds = cloudCoverPercentage,
                windDetails = windDetails,
                humidity = humidity,
                visibility = visibility,
                validDate = date
        )
    }


    /**
     * Transform/convert WeatherDetails object to WeatherForecast
     * @see com.praveen.weatherhub.api.model.WeatherDetails
     * @see com.praveen.weatherhub.room.WeatherForecast
     *
     */
    fun getWeatherForecastEntity(cityName: String, weatherDetails: WeatherDetails) : WeatherForecast {
        val weatherDescription = weatherDetails.weatherDescription
        val weatherIcon = weatherDetails.weatherIcon
        val temperature = weatherDetails.currTemp
        val maxTemperature = weatherDetails.maxTemp
        val minTemperature = weatherDetails.minTemp
        val cloudCoverPercentage = weatherDetails.clouds
        val pressure = weatherDetails.pressure
        val windDetails = weatherDetails.windDetails
        val humidity = weatherDetails.humidity
        val visibility = weatherDetails.visibility
        val validDate = weatherDetails.validDate

        return WeatherForecast(cityName = cityName,
                weatherDescription = weatherDescription,
                weatherIcon = weatherIcon,
                currTemp = temperature,
                maxTemp = maxTemperature,
                minTemp = minTemperature,
                clouds = cloudCoverPercentage,
                pressure = pressure,
                windDetails = windDetails,
                humidity = humidity,
                visibility = visibility,
                validDate = validDate
        )
    }

    /**
     * Transform/convert WeatherForecast object to WeatherDetails
     * @see com.praveen.weatherhub.api.model.WeatherDetails
     * @see com.praveen.weatherhub.room.WeatherForecast
     *
     */
    fun getWeatherForecastDetails(weatherItem: WeatherForecast): WeatherDetails {
        val cityName = weatherItem.cityName
        val weatherDescription = weatherItem.weatherDescription
        val weatherIcon = weatherItem.weatherIcon
        val temperature = weatherItem.currTemp
        val maxTemperature = weatherItem.maxTemp
        val minTemperature = weatherItem.minTemp
        val cloudCoverPercentage: Double? = weatherItem.clouds
        val pressure = weatherItem.pressure
        val windDetails = weatherItem.windDetails
        val humidity = weatherItem.humidity
        val visibility = weatherItem.visibility
        val validDate = weatherItem.validDate

        return WeatherDetails(cityName = cityName,
                weatherDescription = weatherDescription,
                weatherIcon = weatherIcon,
                currTemp = temperature,
                maxTemp = maxTemperature,
                minTemp = minTemperature,
                clouds = cloudCoverPercentage,
                pressure = pressure,
                windDetails = windDetails,
                humidity = humidity,
                visibility = visibility,
                validDate = validDate
        )
    }
}