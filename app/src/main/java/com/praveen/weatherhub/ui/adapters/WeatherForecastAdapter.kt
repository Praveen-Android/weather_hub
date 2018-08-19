package com.praveen.weatherhub.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.praveen.weatherhub.R
import com.praveen.weatherhub.model.networkmodel.WeatherDetails
import com.praveen.weatherhub.utils.AppConstants
import com.praveen.weatherhub.utils.AppUtils.precisionHandler
import java.util.*


class WeatherForecastAdapter(val list: ArrayList<WeatherDetails>) : RecyclerView.Adapter<WeatherForecastAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.weather_forecast_row, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list.get(position)
        holder.minTemp.text = precisionHandler(item.minTemp, AppConstants.CELSIUS_UNIT).plus("/")
        holder.maxTemp.text = precisionHandler(item.maxTemp, AppConstants.CELSIUS_UNIT)
        holder.dayOfWeek.text = item.validDate
        holder.weatherDescription.text = item.weatherDescription
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    class ViewHolder(view: View)  : RecyclerView.ViewHolder(view) {
        var maxTemp : TextView = view.findViewById(R.id.max_temp)
        var minTemp: TextView = view.findViewById(R.id.min_temp)
        var dayOfWeek: TextView = view.findViewById(R.id.day_of_week)
        var weatherDescription: TextView = view.findViewById(R.id.weather_description)
    }
}