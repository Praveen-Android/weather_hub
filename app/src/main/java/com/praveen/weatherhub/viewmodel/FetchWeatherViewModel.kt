package com.praveen.weatherhub.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.praveen.weatherhub.model.networkmodel.WeatherDetails
import com.praveen.weatherhub.repo.WeatherRepository
import com.praveen.weatherhub.viewmodel.Response.Companion.success
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FetchWeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val response:MutableLiveData<Response> = MutableLiveData()

    fun response():MutableLiveData<Response> = response

    fun onSearchDataEntered(searchString: String){
        compositeDisposable.add(weatherRepository.getCurrentWeather(searchString)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { response.postValue(Response.loading())}
                .subscribe(
                        { weatherDetails: WeatherDetails? -> response.postValue(success(weatherDetails)) },
                        { throwable: Throwable? -> response.postValue(Response.error(throwable))}
                ))
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

    fun saveCurrentWeatherToDb(weatherDetails: WeatherDetails){
        weatherRepository.saveCurrentWeatherToDb(weatherDetails)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}