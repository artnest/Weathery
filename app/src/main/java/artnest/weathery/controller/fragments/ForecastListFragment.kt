package artnest.weathery.controller.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import artnest.weathery.App
import artnest.weathery.model.data.Cities
import artnest.weathery.model.data.WeatheryPrefs
import artnest.weathery.model.gson.ExtendedWeather
import artnest.weathery.view.ForecastListFragmentUI
import co.metalab.asyncawait.async
import co.metalab.asyncawait.awaitSuccessful
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast

class ForecastListFragment : Fragment() {

    private lateinit var forecastListFragmentUI: ForecastListFragmentUI

    private var mWeather: ExtendedWeather? = null

    companion object {
        fun newInstance(weather: ExtendedWeather?): ForecastListFragment {
            return ForecastListFragment().apply {
                arguments = Bundle(1).apply {
                    putParcelable(ForecastParentFragment.WEATHER_DATA, weather)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        forecastListFragmentUI = ForecastListFragmentUI()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val v = forecastListFragmentUI.createView(AnkoContext.create(ctx, this))
        (activity as AppCompatActivity).setSupportActionBar(forecastListFragmentUI.tb)
        return v
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            mWeather = arguments.getParcelable<ExtendedWeather>(ForecastParentFragment.WEATHER_DATA)
        }
    }

    override fun onResume() {
        super.onResume()

        async {
//            mWeather = awaitSuccessful(App.openWeather.getForecast(Cities.Minsk.id))
            mWeather = awaitSuccessful(App.openWeather.getForecast(Cities.values()[WeatheryPrefs.selectedCity].id))
            ForecastParentFragment.mWeatherData = mWeather
            forecastListFragmentUI.tv.text = mWeather!!.city.name
        }.onError {
            toast(ForecastParentFragment.getErrorMessage(it.cause!!))
        }
    }
}
