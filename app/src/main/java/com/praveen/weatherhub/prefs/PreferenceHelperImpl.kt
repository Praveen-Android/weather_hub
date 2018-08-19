package com.praveen.weatherhub.prefs

import android.content.Context
import android.content.SharedPreferences

import javax.inject.Inject

class PreferenceHelperImpl @Inject
constructor(context: Context) : PreferenceHelper {

    private val mPrefs: SharedPreferences

    init {
        mPrefs = context.getSharedPreferences(APPLICATION_PREFERENCES_KEY, Context.MODE_PRIVATE)
    }

    override var currentLocation: String
        get() = mPrefs.getString(PREF_KEY_USER_CURRENT_LOCATION, " ")
        set(location) = mPrefs.edit().putString(PREF_KEY_USER_CURRENT_LOCATION, location).apply()

    override var currentLatitude: String
        get() = mPrefs.getString(PREF_KEY_USER_CURRENT_LATITUDE, " ")
        set(lat) = mPrefs.edit().putString(PREF_KEY_USER_CURRENT_LATITUDE, lat).apply()

    override var currentLongitude: String
        get() = mPrefs.getString(PREF_KEY_USER_CURRENT_LONGITUDE, " ")
        set(lon) = mPrefs.edit().putString(PREF_KEY_USER_CURRENT_LONGITUDE, lon).apply()

    companion object {

        val APPLICATION_PREFERENCES_KEY = "weatherhub_prefs"

        private val PREF_KEY_USER_CURRENT_LOCATION = "PREF_KEY_USER_CURRENT_LOCATION"

        private val PREF_KEY_USER_CURRENT_LATITUDE = "PREF_KEY_USER_CURRENT_LATITUDE"

        private val PREF_KEY_USER_CURRENT_LONGITUDE = "PREF_KEY_USER_CURRENT_LONGITUDE"
    }
}
