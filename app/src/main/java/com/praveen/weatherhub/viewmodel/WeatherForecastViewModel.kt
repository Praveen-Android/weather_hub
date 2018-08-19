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

class WeatherForecastViewModel @Inject constructor(private val weatherRepository: WeatherRepository): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val response:MutableLiveData<Response> = MutableLiveData()

    fun response():MutableLiveData<Response> = response

    fun getWeatherForecast(latitude: String, longitude: String){
        compositeDisposable.add(weatherRepository.getWeatherForecast(latitude, longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { response.value = Response.loading() }
                .doOnSuccess{weatherRepository.saveWeatherForecastToDb(it)}
                .subscribe(
                        { weatherDetails: List<WeatherDetails>? -> response.value = success(weatherDetails) },
                        { throwable: Throwable? -> response.value = Response.error(throwable) }
                ))
    }

    fun getWeatherForecastFromDb(cityName:String){
        compositeDisposable.add(weatherRepository.getWeatherForecastFromDb(cityName)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe { response.value = Response.loading() }
                        .doOnSuccess{weatherRepository.saveWeatherForecastToDb(it)}
                        .subscribe(
                                { weatherDetails: List<WeatherDetails>? -> response.value = success(weatherDetails) },
                                { throwable: Throwable? -> response.value = Response.error(throwable) }
                        ))
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}