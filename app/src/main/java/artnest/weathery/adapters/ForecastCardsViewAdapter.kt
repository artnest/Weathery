package artnest.weathery.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import artnest.weathery.App
import artnest.weathery.controller.fragments.ForecastCardsFragment
import artnest.weathery.controller.fragments.ForecastParentFragment
import artnest.weathery.helpers.Common
import artnest.weathery.model.data.Cities
import artnest.weathery.model.data.WeatherDay
import artnest.weathery.model.data.WeatheryPrefs
import artnest.weathery.model.gson.WeatherForecastElement
import artnest.weathery.view.ForecastCardUI
import artnest.weathery.view.ForecastCardViewHolder
import co.metalab.asyncawait.async
import co.metalab.asyncawait.awaitSuccessful
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.toast


class ForecastCardsViewAdapter(val fr: ForecastCardsFragment) : RecyclerView.Adapter<ForecastCardViewHolder>() {

    var weatherDays = emptyList<List<WeatherForecastElement>>()

    init {
        if (fr.mWeather != null) {
            weatherDays = (fr.parentFragment as ForecastParentFragment)
                    .getWeatherDays(fr.mWeather!!.weatherForecastElement)
            fr.forecastCardsFragmentUI.tb.title = Cities.values()[WeatheryPrefs.selectedCity].name
            notifyDataSetChanged()
        } else {
            reload()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ForecastCardViewHolder(
                    ForecastCardUI().createView(AnkoContext.create(parent.context, parent))
            )

    override fun onBindViewHolder(holder: ForecastCardViewHolder, position: Int) {
        val weatherDayList = weatherDays[position]
        val item = weatherDayList[weatherDayList.size / 2]
        holder.bind(getWeatherDay(item))
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
            fr.forecastCardsFragmentUI.tb.title = Cities.values()[WeatheryPrefs.selectedCity].name
            notifyDataSetChanged()
        }.onError {
            fr.toast(ForecastParentFragment.getErrorMessage(it.cause!!))
        }
    }

    private fun getWeatherDay(item: WeatherForecastElement) =
            WeatherDay(
                    Common.getImage(item.weather[0].icon),
                    Common.getDate(item.dtTxt),
                    item.weather[0].description,
                    Common.getTemperature(item.main.temp),
                    Common.getClouds(item.clouds.all),
                    Common.getPressure(item.main.pressure)
            )
}
