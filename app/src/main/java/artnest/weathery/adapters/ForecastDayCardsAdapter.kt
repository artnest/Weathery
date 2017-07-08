package artnest.weathery.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import artnest.weathery.R
import artnest.weathery.controller.activities.ForecastDayCardsActivity
import artnest.weathery.helpers.inflate
import artnest.weathery.helpers.toWeatherInfoDetails
import artnest.weathery.view.ForecastDetailsCardViewHolder


class ForecastDayCardsAdapter(fact: ForecastDayCardsActivity) : RecyclerView.Adapter<ForecastDetailsCardViewHolder>() {

    val weatherHours = fact.mWeatherHoursList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ForecastDetailsCardViewHolder(parent.inflate(R.layout.forecast_details_card))

    override fun onBindViewHolder(holder: ForecastDetailsCardViewHolder, position: Int) =
            holder.bind(weatherHours[position].toWeatherInfoDetails())

    override fun getItemCount() = weatherHours.size
}
