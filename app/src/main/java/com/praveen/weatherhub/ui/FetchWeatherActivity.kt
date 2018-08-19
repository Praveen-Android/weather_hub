package com.praveen.weatherhub.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.jakewharton.rxbinding2.widget.RxTextView
import com.praveen.weatherhub.R
import com.praveen.weatherhub.WeatherApplication
import com.praveen.weatherhub.model.networkmodel.WeatherDetails
import com.praveen.weatherhub.utils.AppConstants.INTENT_EXTRA
import com.praveen.weatherhub.utils.AppUtils
import com.praveen.weatherhub.viewmodel.FetchWeatherViewModel
import com.praveen.weatherhub.viewmodel.Response
import com.praveen.weatherhub.viewmodel.Status
import com.praveen.weatherhub.viewmodel.WeatherViewModelFactory
import hideKeyboard
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject



class FetchWeatherActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: WeatherViewModelFactory
    private lateinit var viewModel: FetchWeatherViewModel
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        WeatherApplication.appComponent.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FetchWeatherViewModel::class.java)

        viewModel.response().observe(this, Observer { response -> processResponse(response!!) })

        val itemInputNameObservable = RxTextView.textChanges(auto_textview)
                .map { inputString: CharSequence -> inputString.isEmpty() || !AppUtils.isValidCityInput(inputString.toString()) }
                .distinctUntilChanged()

        compositeDisposable.add(setupTextInputObserver(itemInputNameObservable))

        setupSearchedCityClickedListener()
    }

    private fun setupSearchedCityClickedListener() {
        show_weather_button.setOnClickListener {
                show_weather_button.isEnabled = false;
                it.hideKeyboard()
                viewModel.onSearchDataEntered(auto_textview.text.toString());
            }
    }

    private fun setupTextInputObserver(itemInputNameObservable: Observable<Boolean>): Disposable {
        return itemInputNameObservable.subscribe { inputIsEmpty: Boolean ->
            input_text.isErrorEnabled = inputIsEmpty
            show_weather_button?.isEnabled = !inputIsEmpty
        }
    }

    private fun navigateToCurrentWeatherActivity(weatherDetails: WeatherDetails?) {
        val intent = Intent(this, CurrentWeatherActivity::class.java)
        intent.putExtra(INTENT_EXTRA, weatherDetails)
        startActivity(intent)
    }

    private fun processResponse(response: Response){
        when(response.status) {
            Status.LOADING -> { showProcessingUI()}

            Status.SUCCESS -> handleResponseData(response)

            Status.ERROR -> handleErrorResponse(response)
        }
    }

    private fun handleResponseData(response:Response){
        showProcessCompleteUI()
        if(response.data is WeatherDetails) {
            viewModel.saveCurrentLocation(response.data.cityName!!)
            viewModel.saveCurrentLatitude(response.data.lat!!)
            viewModel.saveCurrentLongitude(response.data.lon!!)
            viewModel.saveCurrentWeatherToDb(response.data)
            navigateToCurrentWeatherActivity(weatherDetails = response.data)
        }
    }

    private fun handleErrorResponse(response: Response){
        showProcessCompleteUI()
        Toast.makeText(this, getString(R.string.api_error_string), LENGTH_LONG).show()
    }

    override fun onStop() {
        compositeDisposable.clear();
        super.onStop()
    }

    private fun showProcessingUI(){
        text_input_layout.isEnabled = false
        progressBar.visibility = View.VISIBLE
        text_input_layout.alpha = 0.5f
        show_weather_button.isEnabled = false
    }

    private fun showProcessCompleteUI(){
        text_input_layout.isEnabled = true
        progressBar.visibility = View.INVISIBLE
        text_input_layout.alpha = 1f
        show_weather_button.isEnabled = true
    }
}
