package com.praveen.weatherhub

import android.app.Application
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import com.praveen.weatherhub.di.*
import com.praveen.weatherhub.scheduler.WeatherJobService

class WeatherApplication : Application() {
    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        setUpDagger()
        setUpWeatherJob()
    }

    private fun setUpDagger() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .roomModule(RoomModule())
                .networkModule(NetworkModule()).build()
    }


    private fun setUpWeatherJob() {
        val jobId = 999
        val interval = 360000L
        val jobScheduler = getSystemService(Application.JOB_SCHEDULER_SERVICE) as JobScheduler
        val serviceName = ComponentName(this, WeatherJobService::class.java)
        val jobInfo = JobInfo.Builder(jobId, serviceName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPeriodic(interval)
                .build()
        jobScheduler.schedule(jobInfo)
    }
}