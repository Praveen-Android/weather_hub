package com.praveen.weatherhub.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    fun addFragmentToActivity(fragmentManager: FragmentManager,
                              fragment: Fragment,
                              frameId: Int,
                              tag: String) {

        val transaction = fragmentManager.beginTransaction()
        transaction.replace(frameId, fragment, tag)
        transaction.commit()
    }
}
