package com.praveen.weatherhub

import com.praveen.weatherhub.api.GeocodingService
import com.praveen.weatherhub.api.WeatherDataSource
import com.praveen.weatherhub.api.WeatherService
import com.praveen.weatherhub.model.locationmodel.*
import com.praveen.weatherhub.model.locationmodel.bounds.Bounds
import com.praveen.weatherhub.model.locationmodel.bounds.Northeast
import com.praveen.weatherhub.model.locationmodel.bounds.Southwest
import com.praveen.weatherhub.model.locationmodel.viewport.Viewport
import com.praveen.weatherhub.model.networkmodel.CurrentWeatherItem
import com.praveen.weatherhub.model.networkmodel.WeatherDetails
import com.praveen.weatherhub.model.networkmodel.WeatherResponse
import com.praveen.weatherhub.prefs.PreferenceHelper
import com.praveen.weatherhub.repo.WeatherRepository
import com.praveen.weatherhub.repo.WeatherRepositoryImpl
import com.praveen.weatherhub.room.CurrentWeather
import com.praveen.weatherhub.room.WeatherDb
import com.praveen.weatherhub.room.WeatherHubDao
import com.praveen.weatherhub.utils.ModelUtils
import io.reactivex.Single
import junit.framework.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WeatherRepositoryTest {

    @Mock
    private lateinit var weatherDb: WeatherDb

    @Mock
    private lateinit var weatherService: WeatherService

    @Mock
    private lateinit var weatherHubDao: WeatherHubDao

    @Mock
    private lateinit var preferenceHelper: PreferenceHelper

    @Mock
    private lateinit var geocodingService: GeocodingService

    @Captor
    private lateinit var stringLatitudeArgumentCaptor: ArgumentCaptor<String>

    @Captor
    private lateinit var stringLongitudeArgumentCaptor: ArgumentCaptor<String>

    @Captor
    private lateinit var currentWeatherArgumentCaptor: ArgumentCaptor<CurrentWeather>

    private lateinit var weatherDataSource: WeatherDataSource

    private lateinit var weatherRepository: WeatherRepository


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        weatherDataSource = WeatherDataSource(weatherService, geocodingService)
        weatherRepository = WeatherRepositoryImpl(weatherDataSource, weatherDb, preferenceHelper)
        Mockito.`when`(weatherDb.weatherHubDao()).thenReturn(weatherHubDao)
    }

    @Test
    fun testCurrentWeatherEntryToDb() {
        Mockito.`when`(weatherDb.weatherHubDao().getCurrentWeather("dublin")).
                thenReturn(Single.just(ModelUtils.getCurrentWeatherEntity("dublin",weatherDetailsMock)))
        weatherRepository.getCurrentWeatherFromDb("dublin").test().assertValueCount(1)
    }

    @Test
    fun testEmptyCurrentWeatherEntryInDb() {
        Mockito.`when`(weatherDb.weatherHubDao().getCurrentWeather("dublin")).thenReturn(Single.error(Throwable()))
        weatherRepository.getCurrentWeatherFromDb("dublin").test().assertValueCount(0)
    }

    @Test
    fun testInsertCurrentWeatherToDb(){
        val insertedCityName = "dublin,CA,US"
        weatherRepository.setCurrentLocation("dublin")

        weatherRepository.saveCurrentWeatherToDb(weatherDetailsMock)

        Mockito.verify(weatherDb.weatherHubDao()).insert(capture(currentWeatherArgumentCaptor))

        Assert.assertEquals(insertedCityName, currentWeatherArgumentCaptor.value.cityName)
    }

    @Test
    fun testCurrentWeatherRequest() {
        val locationResponse = LocationResponse(listOf(resultMock), "OK")
        val weatherResponse = WeatherResponse(listOf(weatherItemMock), 1)
        val searchedCity = "Dublin"
        val latitude = weatherResponse.data!![0]!!.lat
        val longitude = weatherResponse.data!![0]!!.lon

        Mockito.`when`(geocodingService.requestCityAddressByName(searchedCity)).thenReturn(Single.just(locationResponse))
        Mockito.`when`(weatherService.requestCurrentWeather(weatherResponse.data!![0]!!.city_name!!, "1234")).thenReturn(Single.just(weatherResponse))

        weatherRepository.getCurrentWeather(searchedCity).test()
                .assertNoErrors()
                .assertValue { weatherDetails: WeatherDetails ->
                    weatherDetails == ModelUtils.getCurrentWeatherDetails(weatherResponse)
                }

        Mockito.verify<WeatherService>(weatherService).requestCurrentWeather(capture(stringLatitudeArgumentCaptor), capture(stringLongitudeArgumentCaptor))

        Assert.assertEquals(latitude, stringLatitudeArgumentCaptor.value)
        Assert.assertEquals(longitude, stringLongitudeArgumentCaptor.value)
    }




    companion object {

        val weatherMock = com.praveen.weatherhub.model.networkmodel.CurrentWeatherData(icon = "c01d",
                code =  "800",
                description = "Clear sky")

        val weatherItemMock = CurrentWeatherItem(wind_cdir = "W",
        rh = 45.0,
        pod = "d",
        lon = -121.93579,
        pres =  997.2,
        timezone =  "America/Los_Angeles",
        ob_time =  "2018-07-28 20:48",
        country_code =  "US",
        clouds =  25.0,
        vis =  10.0,
        state_code =  "CA",
        wind_spd = 3.6,
        lat = 37.70215,
        wind_cdir_full = "west",
        slp =  1011.8,
        datetime =  "2018-07-28:20",
        ts =  1532810880,
        station =  "D4201",
        hAngle =  0.0,
        dewpt = 18.8,
        uv = 8.69044,
        dni =  852.242,
        elevAngle =  70.8028,
        ghi =  945.621,
        dhi =  140.742,
        precip =  null,
        city_name =  "Dublin",
        sunset =  "03:20",
        temp =  32.2,
        sunrise =  "13:08",
        weather = weatherMock,
        app_temp =  33.7)

        val weatherDetailsMock = WeatherDetails(cityName = "dublin",
        time = "2018-07-28 20:48",
        weatherDescription = "clear skies",
        weatherIcon = "800",
        pod = "d",
        currTemp = 17.0,
        pressure = 997.5,
        windDetails = "0.8 SE",
        visibility = 10.0,
        clouds = 10.0,
        humidity = 10.0,
        validDate = null,
        lat = "37.70215",
        lon = "-121.93579"
        )

        val addressMock = AddressComponent(
                "Dublin, California", "California", listOf("94555")
        )

        val northEastBoundsMock = Northeast(
                12.34, 56.78
        )

        val southEastBoundMock = Southwest(
                11.11, 22.33
        )

        val nortEastViewportMock = com.praveen.weatherhub.model.locationmodel.viewport.Northeast(
                12.34, 56.78
        )


        val southWestViewportMock = com.praveen.weatherhub.model.locationmodel.viewport.Southwest(
                11.11, 22.33
        )

        val boundsMock = Bounds(
                northEastBoundsMock, southEastBoundMock
        )

        val viewPortMock = Viewport(
                nortEastViewportMock, southWestViewportMock
        )

        val geometryMock = Geometry(
                boundsMock, Location(12.12, 34.56), "abcd", viewPortMock
        )

        val resultMock = Result(
                listOf(addressMock), "Address", geometryMock, "12", listOf("12")
        )

    }

    fun <T> capture(argumentCaptor: ArgumentCaptor<T>): T = argumentCaptor.capture()
}
