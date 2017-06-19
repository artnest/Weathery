package artnest.weathery.controller.fragments

import android.os.Bundle
import android.support.v4.app.ListFragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import artnest.weathery.model.gson.ExtendedWeather
import artnest.weathery.view.ForecastListFragmentUI
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.ctx

class ForecastListFragment : ListFragment() {

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
        (act as AppCompatActivity).setSupportActionBar(forecastListFragmentUI.tb)
        /*var weather: ExtendedWeather? = null
        async {
            weather = awaitSuccessful(App.openWeather
                    .getForecast(Cities.values()[WeatheryPrefs.selectedCity].id))
            forecastListFragmentUI.lv.adapter = ListViewAdapter(weather!!)
        }.onError {
            toast("err")
        }*/
//        forecastListFragmentUI.lv.adapter = ListViewAdapter(parentFragment as ForecastParentFragment)
        return v
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            mWeather = arguments.getParcelable<ExtendedWeather>(ForecastParentFragment.WEATHER_DATA)
        }
    }
}
