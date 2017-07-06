package artnest.weathery.controller.fragments

import android.os.Bundle
import android.support.v4.app.ListFragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import artnest.weathery.controller.activities.ForecastDayActivity
import artnest.weathery.model.gson.ExtendedWeather
import artnest.weathery.view.ForecastListFragmentUI
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.startActivity

class ForecastListFragment : ListFragment() {

    lateinit var forecastListFragmentUI: ForecastListFragmentUI

    var mWeather: ExtendedWeather? = null

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

        if (arguments != null) {
            mWeather = arguments.getParcelable<ExtendedWeather>(ForecastParentFragment.WEATHER_DATA)
        }

        forecastListFragmentUI = ForecastListFragmentUI()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val v = forecastListFragmentUI.createView(AnkoContext.create(ctx, this))
        (act as AppCompatActivity).setSupportActionBar(forecastListFragmentUI.tb)
        return v
    }

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        startActivity<ForecastDayActivity>(
                ForecastDayActivity.CITY_NAME to
                        (parentFragment as ForecastParentFragment).mWeatherData!!.city.name,
                ForecastDayActivity.WEATHER_HOURS_DATA to listView!!.getItemAtPosition(position)
        )
    }
}
