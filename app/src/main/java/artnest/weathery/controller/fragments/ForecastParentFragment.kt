package artnest.weathery.controller.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import artnest.weathery.R
import artnest.weathery.adapters.ForecastListViewAdapter
import artnest.weathery.model.data.WeatheryPrefs
import artnest.weathery.model.gson.ExtendedWeather
import artnest.weathery.model.gson.WeatherForecastElement
import artnest.weathery.view.ForecastParentFragmentUI
import co.metalab.asyncawait.RetrofitHttpError
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.ctx

class ForecastParentFragment : Fragment() {

    var mWeatherData: ExtendedWeather? = null

    companion object {
        val WEATHER_DATA = "weather_data"

        fun getErrorMessage(it: Throwable) =
                if (it is RetrofitHttpError) {
                    val httpErrorCode = it.errorResponse.code()
                    val errorResponse = it.errorResponse.message()
                    "[$httpErrorCode] $errorResponse"
                } else {
                    "Couldn't refresh forecast"
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) =
            ForecastParentFragmentUI().createView(AnkoContext.create(ctx, this))

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        childFragmentManager.beginTransaction()
                .replace(R.id.child_fragment_container,
                        if (WeatheryPrefs.forecastType == 0) {
                            ForecastListFragment.newInstance(mWeatherData)
                        } else {
                            ForecastCardsFragment.newInstance(mWeatherData)
                        })
                .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_refresh -> {
                // TODO change to when(...) (fragment check)
                if (WeatheryPrefs.forecastType == 0) {
                    ((childFragmentManager
                            .findFragmentById(R.id.child_fragment_container) as ForecastListFragment)
                            .forecastListFragmentUI.lv.adapter as ForecastListViewAdapter)
                            .reload()
                } else {
                    // TODO ForecastCardsFragment
                }

                return true
            }

            R.id.action_view_list -> {
                WeatheryPrefs.forecastType = 1
                activity.supportInvalidateOptionsMenu()

                childFragmentManager.beginTransaction()
                        .replace(R.id.child_fragment_container,
                                ForecastCardsFragment.newInstance(mWeatherData))
                        .commit()

                return true
            }

            R.id.action_view_cards -> {
                WeatheryPrefs.forecastType = 0
                activity.supportInvalidateOptionsMenu()

                childFragmentManager.beginTransaction()
                        .replace(R.id.child_fragment_container,
                                ForecastListFragment.newInstance(mWeatherData))
                        .commit()

                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun getWeatherDays(list: List<WeatherForecastElement>): List<List<WeatherForecastElement>> {
        val weatherDaysList = mutableListOf<List<WeatherForecastElement>>()

        var dateDay = list.first().dtTxt.substringBefore(" ")
        var weatherDayWithHours = mutableListOf<WeatherForecastElement>()
        for (weatherHour in list) {
            if (weatherHour.dtTxt.substringBefore(" ") == dateDay) {
                weatherDayWithHours.add(weatherHour)

                if (weatherHour == list.last()) {
                    weatherDaysList.add(weatherDayWithHours)
                }
            } else {
                weatherDaysList.add(weatherDayWithHours)
                weatherDayWithHours = mutableListOf(weatherHour)
                dateDay = weatherHour.dtTxt.substringBefore(" ")
            }
        }

        return weatherDaysList
    }
}