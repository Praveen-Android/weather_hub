package com.praveen.weatherhub.repo

import com.praveen.weatherhub.api.WeatherDataSource
import com.praveen.weatherhub.model.locationmodel.LocationResponse
import com.praveen.weatherhub.model.networkmodel.WeatherDetails
import com.praveen.weatherhub.model.networkmodel.WeatherForecastItem
import com.praveen.weatherhub.model.networkmodel.WeatherForecastResponse
import com.praveen.weatherhub.model.networkmodel.WeatherResponse
import com.praveen.weatherhub.prefs.PreferenceHelper
import com.praveen.weatherhub.room.CurrentWeather
import com.praveen.weatherhub.room.WeatherDb
import com.praveen.weatherhub.room.WeatherForecast
import com.praveen.weatherhub.utils.ModelUtils
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(private val weatherService: WeatherDataSource,
                                                private val weatherDb: WeatherDb,
                                                private val preferenceHelper: PreferenceHelper) : WeatherRepository {

    //API operations
    override fun getCurrentWeather(cityName: String): Single<WeatherDetails> {
        return weatherService.requestCityAddressByName(cityName)
                .flatMap({ locationResponse: LocationResponse ->
                    weatherService.requestCurrentWeather(
                            locationResponse.results[0].geometry.location.lat.toString(),
                            locationResponse.results[0].geometry.location.lng.toString())
                            .map { weatherResponse: WeatherResponse ->
                                ModelUtils.getCurrentWeatherDetails(weatherResponse)
                            }

                })
    }

    override fun getCurrentWeather(latitude: String, longitude:String): Single<WeatherDetails> {
        return weatherService.requestCurrentWeather(latitude, longitude)
                .map { weatherResponse: WeatherResponse ->
                    ModelUtils.getCurrentWeatherDetails(weatherResponse)
                }
    }

    override fun processCurrentLocation(latitude: Double, longitude: Double): Single<LocationResponse> {
        return weatherService.requestCityAddressByLatLng(latitude.toString().plus(",").plus(longitude.toString()))
    }

    override fun getWeatherForecast(cityName: String): Single<List<WeatherDetails>> {
        val selectedCity = preferenceHelper.currentLocation

        val weatherItems: Single<List<WeatherForecastItem?>> =
                weatherService.requestWeatherForecast(selectedCity)
                        .map { weatherResponse: WeatherForecastResponse -> weatherResponse.data }

        return weatherItems.toObservable()
                .flatMap { list -> Observable.fromIterable(list) }
                .map { item -> ModelUtils.getWeatherForecastDetails(selectedCity, item) }
                .toList()
    }

    override fun getWeatherForecast(latitude: String, longitude: String): Single<List<WeatherDetails>> {
        val weatherItems: Single<List<WeatherForecastItem?>> =
                weatherService.requestWeatherForecast(latitude, longitude)
                        .map { weatherResponse: WeatherForecastResponse -> weatherResponse.data }

        return weatherItems.toObservable()
                .flatMap { list -> Observable.fromIterable(list) }
                .map { item -> ModelUtils.getWeatherForecastDetails(preferenceHelper.currentLocation, item) }
                .toList()
    }


    // Shared Preferences Operations
    override fun setCurrentLocation(cityName: String) {
        preferenceHelper.currentLocation = cityName
    }

    override fun getCurrentLocation(): String {
        return preferenceHelper.currentLocation
    }

    override fun getCurrentLatitude(): String {
        return preferenceHelper.currentLatitude
    }

    override fun setCurrentLatitude(latitude: String) {
        preferenceHelper.currentLatitude = latitude
    }

    override fun getCurrentLongitude(): String {
        return preferenceHelper.currentLongitude
    }

    override fun setCurrentLongitude(longitude: String) {
        preferenceHelper.currentLongitude = longitude
    }


    //DB Operations
    override fun saveCurrentWeatherToDb(weatherDetail: WeatherDetails) {
        Single.just(weatherDetail)
                .subscribeOn(Schedulers.io())
                .map { weatherDb.weatherHubDao().insert(ModelUtils.getCurrentWeatherEntity(getCurrentLocation(), weatherDetail)) }
                .subscribe()
    }

    override fun getCurrentWeatherFromDb(cityName: String): Single<WeatherDetails> {
        return weatherDb.weatherHubDao().getCurrentWeather(cityName)
                .map { currentWeather: CurrentWeather -> ModelUtils.getCurrentWeatherDetails(currentWeather) }

    }

    override fun saveWeatherForecastToDb(weatherDetailsList: List<WeatherDetails>) {
        val weatherForecast: Single<List<WeatherForecast>>
        weatherForecast = Observable.just(weatherDetailsList)
                .flatMap { list -> Observable.fromIterable(list) }
                .map { item -> ModelUtils.getWeatherForecastEntity(preferenceHelper.currentLocation, item) }
                .toList()

        weatherForecast
                .subscribeOn(Schedulers.io())
                .map { weatherDb.weatherHubDao().insertAll(it) }
                .subscribe()
    }

    override fun getWeatherForecastFromDb(cityName: String): Single<List<WeatherDetails>> {
        return weatherDb.weatherHubDao().getWeatherForecast(cityName)
                .toObservable()
                .flatMap { list -> Observable.fromIterable(list) }
                .map { item -> ModelUtils.getWeatherForecastDetails(item) }
                .toList()
    }

    //Scheduler
    override fun scheduleWeatherJob(): Single<WeatherDetails> {
        return  getCurrentWeather(preferenceHelper.currentLatitude, preferenceHelper.currentLongitude)
    }
}