package artnest.weathery.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import artnest.weathery.R
import artnest.weathery.controller.activities.ForecastDayCardsActivity
import artnest.weathery.helpers.Common
import artnest.weathery.helpers.inflate
import artnest.weathery.model.data.WeatherDetails
import artnest.weathery.model.gson.WeatherForecastElement
import artnest.weathery.view.ForecastDetailsCardViewHolder


class ForecastDayCardsAdapter(fact: ForecastDayCardsActivity) : RecyclerView.Adapter<ForecastDetailsCardViewHolder>() {

    val weatherHours = fact.mWeatherHoursList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ForecastDetailsCardViewHolder(parent.inflate(R.layout.forecast_details_card))

    override fun onBindViewHolder(holder: ForecastDetailsCardViewHolder, position: Int) =
            holder.bind(getWeatherDetails(weatherHours[position]))

    override fun getItemCount() = weatherHours.size

    private fun getWeatherDetails(item: WeatherForecastElement) =
            WeatherDetails(
                    Common.getHours(item.dtTxt),
                    Common.getImage(item.weather[0].icon),
                    item.weather[0].description,
                    Common.getTemperature(item.main.temp),
                    Common.getWind(item.wind.speed),
                    Common.getClouds(item.clouds.all),
                    Common.getPressure(item.main.pressure),
                    Common.getHumidity(item.main.humidity),
                    Common.getRain(item.rain?._3h),
                    Common.getSnow(item.snow?._3h)
            )
}
