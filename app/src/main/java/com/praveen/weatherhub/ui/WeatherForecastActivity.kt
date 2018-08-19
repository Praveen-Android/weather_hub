package com.praveen.weatherhub.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.praveen.weatherhub.R
import com.praveen.weatherhub.WeatherApplication
import com.praveen.weatherhub.model.networkmodel.WeatherDetails
import com.praveen.weatherhub.prefs.PreferenceHelper
import com.praveen.weatherhub.ui.adapters.WeatherForecastAdapter
import com.praveen.weatherhub.viewmodel.Response
import com.praveen.weatherhub.viewmodel.Status
import com.praveen.weatherhub.viewmodel.WeatherForecastViewModel
import com.praveen.weatherhub.viewmodel.WeatherViewModelFactory
import kotlinx.android.synthetic.main.activity_weather_forecast.*
import javax.inject.Inject

class WeatherForecastActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: WeatherViewModelFactory

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    private lateinit var viewModel: WeatherForecastViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_forecast)

        WeatherApplication.appComponent.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(WeatherForecastViewModel::class.java)
        viewModel.getWeatherForecast(preferenceHelper.currentLatitude, preferenceHelper.currentLongitude)

        viewModel.response().observe(this, Observer { response -> processResponse(response!!) })
    }

    private fun processResponse(response: Response){
        when(response.status) {
            Status.LOADING -> progress_bar.visibility = View.VISIBLE

            Status.SUCCESS -> handleResponseData(response)

            Status.ERROR -> handleErrorResponse(response)
        }
    }

    private fun handleResponseData(response: Response){
        progress_bar.visibility = View.INVISIBLE
        if(response.data is List<*>){
            val weatherDetailsList: ArrayList<WeatherDetails> = response.data as ArrayList<WeatherDetails>
            weather_forecast_layout.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
            weather_forecast_layout.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))
            weather_forecast_layout.adapter = WeatherForecastAdapter(weatherDetailsList)
        }
    }

    private fun handleErrorResponse(response: Response){
        progress_bar.visibility = View.INVISIBLE
        Toast.makeText(this, getString(R.string.api_error_string), Toast.LENGTH_LONG).show()
    }
}