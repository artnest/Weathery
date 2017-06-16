package artnest.weathery.controller.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import artnest.weathery.App
import artnest.weathery.R
import artnest.weathery.model.data.Cities
import artnest.weathery.model.data.WeatheryPrefs
import artnest.weathery.model.gson.ExtendedWeather
import artnest.weathery.view.ForecastParentFragmentUI
import co.metalab.asyncawait.RetrofitHttpError
import co.metalab.asyncawait.async
import co.metalab.asyncawait.awaitSuccessful
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast

class ForecastParentFragment : Fragment() {

    private var mWeatherData: ExtendedWeather? = null

    companion object {
        val WEATHER_DATA = "weather_data"
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

    fun getWeatherData(): ExtendedWeather? {
        var weather: ExtendedWeather? = null
        async {
            weather = awaitSuccessful(App.openWeather
                    .getForecast(Cities.values()[WeatheryPrefs.selectedCity].id))
            mWeatherData = weather
        }.onError {
            toast(getErrorMessage(it.cause!!))
        }
        return weather
    }

    private fun getErrorMessage(it: Throwable) =
            if (it is RetrofitHttpError) {
                val httpErrorCode = it.errorResponse.code()
                val errorResponse = it.errorResponse.message()
                "[$httpErrorCode] $errorResponse"
            } else {
                "Couldn't refresh forecast"
            }
}