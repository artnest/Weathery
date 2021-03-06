package artnest.weathery.controller.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import artnest.weathery.model.gson.Weather.ExtendedWeather
import artnest.weathery.view.ForecastCardsFragmentUI
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.ctx

class ForecastCardsFragment : Fragment() {

    lateinit var forecastCardsFragmentUI: ForecastCardsFragmentUI

    var mWeather: ExtendedWeather? = null

    companion object {
        fun newInstance(weather: ExtendedWeather?): ForecastCardsFragment {
            return ForecastCardsFragment().apply {
                arguments = Bundle(1).apply {
                    putParcelable(ForecastParentFragment.WEATHER_DATA, weather)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            mWeather = arguments.getParcelable<ExtendedWeather>(ForecastParentFragment.WEATHER_DATA)
        }

        forecastCardsFragmentUI = ForecastCardsFragmentUI()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val v = forecastCardsFragmentUI.createView(AnkoContext.create(ctx, this))
        (act as AppCompatActivity).setSupportActionBar(forecastCardsFragmentUI.tb)
        if (mWeather != null) {
            forecastCardsFragmentUI.rv.visibility = View.VISIBLE
            forecastCardsFragmentUI.etv.visibility = View.GONE
        } else {
            forecastCardsFragmentUI.rv.visibility = View.GONE
            forecastCardsFragmentUI.etv.visibility = View.VISIBLE
        }
        return v
    }
}
