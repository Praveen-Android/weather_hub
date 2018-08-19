package com.praveen.weatherhub.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.praveen.weatherhub.model.locationmodel.LocationResponse
import com.praveen.weatherhub.model.networkmodel.WeatherDetails
import com.praveen.weatherhub.repo.WeatherRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CurrentWeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val response: MutableLiveData<Response> = MutableLiveData()

    fun response(): MutableLiveData<Response> = response

    fun processCurrentLocation(latitude: Double, longitude:Double){
        compositeDisposable.add(weatherRepository.processCurrentLocation(latitude, longitude)
                .subscribeOn(Schedulers.io())
                .subscribe { locationResponse: LocationResponse -> saveCurrentLocation(locationResponse.results[0].formatted_address, latitude, longitude) }
        )
    }

    private fun saveCurrentLocation(location:String, latitude: Double, longitude: Double){
        weatherRepository.setCurrentLocation(location)

        compositeDisposable.add(weatherRepository.getCurrentWeather(latitude.toString(), longitude.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { response.postValue(Response.loading()) }
                .subscribe(
                        { weatherDetails: WeatherDetails? -> response.postValue(Response.success(weatherDetails))},
                        { throwable: Throwable? -> response.postValue(Response.error(throwable))}
                )
        )
    }

    fun saveCurrentLocation(location:String){
        weatherRepository.setCurrentLocation(location)
    }

    fun saveCurrentLatitude(latitude:String){
        weatherRepository.setCurrentLatitude(latitude)
    }

    fun saveCurrentLongitude(longitude:String){
        weatherRepository.setCurrentLongitude(longitude)
    }

    fun saveCurrentWeatherToDb(weatherDetails: WeatherDetails) {
        weatherRepository.saveCurrentWeatherToDb(weatherDetails)
    }

    fun fetchCurrentWeatherFromDb(){
        compositeDisposable.add(weatherRepository.getCurrentWeatherFromDb(weatherRepository.getCurrentLocation())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { response.postValue(Response.loading()) }
                .subscribe(
                        { weatherDetails: WeatherDetails? -> response.postValue(Response.success(weatherDetails))},
                        { throwable: Throwable? -> response.postValue(Response.error(throwable))}
                ))
    }


    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
