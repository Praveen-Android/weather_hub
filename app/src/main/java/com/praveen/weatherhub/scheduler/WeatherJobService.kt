package com.praveen.weatherhub.scheduler

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import com.praveen.weatherhub.WeatherApplication
import com.praveen.weatherhub.repo.WeatherRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Schedule the start of updating weather every 1 hour
 */

class WeatherJobService : JobService() {

    @Inject
    lateinit var repository: WeatherRepository

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate() {
        super.onCreate()
        WeatherApplication.appComponent.inject(this)
    }

    override fun onStartJob(p0: JobParameters?): Boolean {
        val s = repository.scheduleWeatherJob()!!.subscribeOn(Schedulers.io())
                .doAfterSuccess { WeatherNotificationUtil.generateNotification(applicationContext) }
                .subscribe({ Log.d(javaClass.simpleName, "scheduled job successful")},
                        { Log.d(javaClass.simpleName,it.message?:"Scheduled job failure")})
        compositeDisposable.add(s)
        return true
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        compositeDisposable.clear()
        Log.d(javaClass.simpleName,"Job Scheduler stopped")
        return true
    }
}