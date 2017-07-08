package artnest.weathery.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import artnest.weathery.App
import artnest.weathery.R
import artnest.weathery.controller.fragments.ForecastCardsFragment
import artnest.weathery.controller.fragments.ForecastParentFragment
import artnest.weathery.helpers.Common
import artnest.weathery.helpers.inflate
import artnest.weathery.helpers.toWeatherInfo
import artnest.weathery.model.data.Cities
import artnest.weathery.model.data.WeatheryPrefs
import artnest.weathery.model.gson.WeatherForecastElement
import artnest.weathery.view.ForecastCardViewHolder
import co.metalab.asyncawait.async
import co.metalab.asyncawait.awaitSuccessful
import org.jetbrains.anko.support.v4.toast


class ForecastCardsViewAdapter(val fr: ForecastCardsFragment,
                               val listener: (List<WeatherForecastElement>) -> Unit) : RecyclerView.Adapter<ForecastCardViewHolder>() {

    var weatherDays = emptyList<List<WeatherForecastElement>>()

    init {
        if (fr.mWeather != null) {
            weatherDays = (fr.parentFragment as ForecastParentFragment)
                    .getWeatherDays(fr.mWeather!!.weatherForecastElement)
            fr.forecastCardsFragmentUI.tb.title = fr.mWeather!!.city.name
            notifyDataSetChanged()
        } else {
            reload()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ForecastCardViewHolder(parent.inflate(R.layout.forecast_card))

    override fun onBindViewHolder(holder: ForecastCardViewHolder, position: Int) {
        val weatherDayList = weatherDays[position]
        val item = weatherDayList[weatherDayList.size / 2]
        holder.bind(item.toWeatherInfo(), weatherDayList, listener)
    }

    override fun getItemCount() = weatherDays.size

    fun reload() {
        getWeatherData()
    }

    private fun getWeatherData() {
        async {
            val weather = awaitSuccessful(App.openWeather
                    .getForecast(Cities.values()[WeatheryPrefs.selectedCity].id))
            (fr.parentFragment as ForecastParentFragment).mWeatherData = weather

            weatherDays = (fr.parentFragment as ForecastParentFragment)
                    .getWeatherDays(weather.weatherForecastElement)
            fr.forecastCardsFragmentUI.tb.title = weather.city.name
            fr.forecastCardsFragmentUI.rv.visibility = View.VISIBLE
            fr.forecastCardsFragmentUI.etv.visibility = View.GONE
            notifyDataSetChanged()
        }.onError {
            fr.toast(Common.getErrorMessage(it.cause!!))
        }
    }
}
