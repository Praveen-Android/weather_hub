package com.praveen.weatherhub.ui

import android.Manifest
import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Typeface
import android.location.Location
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.praveen.weatherhub.R
import com.praveen.weatherhub.WeatherApplication
import com.praveen.weatherhub.model.networkmodel.WeatherDetails
import com.praveen.weatherhub.utils.AppConstants
import com.praveen.weatherhub.utils.AppConstants.REQUEST_LOCATION_PERMISSION
import com.praveen.weatherhub.utils.AppConstants.WEATHER_UPDATE_NOTIFICATION
import com.praveen.weatherhub.utils.AppUtils
import com.praveen.weatherhub.viewmodel.CurrentWeatherViewModel
import com.praveen.weatherhub.viewmodel.Response
import com.praveen.weatherhub.viewmodel.Status
import com.praveen.weatherhub.viewmodel.WeatherViewModelFactory
import kotlinx.android.synthetic.main.activity_current_weather.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.EasyPermissions.hasPermissions
import pub.devrel.easypermissions.EasyPermissions.requestPermissions
import javax.inject.Inject

class CurrentWeatherActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    @Inject
    lateinit var viewModelFactory: WeatherViewModelFactory

    private lateinit var viewModel: CurrentWeatherViewModel

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_weather)

        WeatherApplication.appComponent.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CurrentWeatherViewModel::class.java)
        viewModel.response().observe(this, Observer { response -> processResponse(response!!) })


        val weatherDetails: WeatherDetails? = intent?.extras?.getParcelable(AppConstants.INTENT_EXTRA)


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val weatherFont = Typeface.createFromAsset(this.assets, "fonts/weathericons.ttf")
        weather_icon.typeface = weatherFont

        if(weatherDetails!=null){
            updateUI(weatherDetails)
        } else{
            viewModel.fetchCurrentWeatherFromDb()
        }

        get_forecast.setOnClickListener{ navigateToWeatherForecastActivity()}
    }

    override fun onNewIntent(intent: Intent?) {
        (intent?.action==WEATHER_UPDATE_NOTIFICATION).let { viewModel.fetchCurrentWeatherFromDb() }
        super.onNewIntent(intent)
    }

    private fun navigateToWeatherForecastActivity(){
        val intent = Intent(this, WeatherForecastActivity::class.java)
        startActivity(intent)
    }

    private fun updateUI(weatherDetails: WeatherDetails){
        city.text = weatherDetails.cityName
        updated_time.text = weatherDetails.time
        weather_icon.text = AppUtils.getWeatherIcon(this, weatherDetails.weatherIcon!!.toInt(), weatherDetails.pod!!)
        current_temperature.text = AppUtils.precisionHandler(weatherDetails.currTemp, AppConstants.CELSIUS_UNIT)
                .plus("/")
                .plus(AppUtils.precisionHandler(AppUtils.convertCelsiusToFahrenheit(weatherDetails.currTemp), AppConstants.FAHRENHEIT_UNIT))

        more_details.text = getString(R.string.windDetails).plus(weatherDetails.windDetails).plus("\tm/s\n")
                .plus(getString(R.string.humidity)).plus(weatherDetails.humidity).plus("\t%\n")
                .plus(getString(R.string.clouds)).plus(weatherDetails.clouds).plus("\t%\n")
                .plus(getString(R.string.pressure)).plus(weatherDetails.pressure).plus( "\tmb\n")
                .plus(getString(R.string.visibility)).plus(weatherDetails.visibility).plus("\tkm\n")

    }

    private fun requestLocationPermission() {
        if(!hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            requestPermissions(this, "Please grant the location permission", REQUEST_LOCATION_PERMISSION, Manifest.permission.ACCESS_FINE_LOCATION)
        }else{
            showCurrentLocationWeather()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if(requestCode == REQUEST_LOCATION_PERMISSION) {
            showCurrentLocationWeather()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.current_location -> {requestLocationPermission()
            return true}
            else -> return super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("MissingPermission")
    private fun showCurrentLocationWeather(){
        fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    location?.let {  viewModel.processCurrentLocation(location.latitude, location.longitude) }
                }
    }

    private fun processResponse(response: Response){
        when(response.status) {
            Status.LOADING -> progressBar.visibility = View.VISIBLE

            Status.SUCCESS -> handleResponseData(response)

            Status.ERROR -> handleErrorResponse(response)
        }
    }

    private fun handleResponseData(response:Response){
        progressBar.visibility = View.INVISIBLE
        if(response.data is WeatherDetails) {
            viewModel.saveCurrentLocation(response.data.cityName!!)
            viewModel.saveCurrentLatitude(response.data.lat!!)
            viewModel.saveCurrentLongitude(response.data.lon!!)
            viewModel.saveCurrentWeatherToDb(response.data)
            updateUI(response.data);
        }
    }

    private fun handleErrorResponse(response: Response){
        progressBar.visibility = View.INVISIBLE
        Toast.makeText(this, getString(R.string.api_error_string), Toast.LENGTH_LONG).show()
    }
}
