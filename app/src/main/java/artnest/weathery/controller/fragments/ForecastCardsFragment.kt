package artnest.weathery.controller.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import artnest.weathery.model.gson.ExtendedWeather
import artnest.weathery.view.ForecastCardsFragmentUI
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.ctx

class ForecastCardsFragment : Fragment() {

    private lateinit var forecastCardsFragmentUI: ForecastCardsFragmentUI

    private var mWeather: ExtendedWeather? = null

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

        forecastCardsFragmentUI = ForecastCardsFragmentUI()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val v = forecastCardsFragmentUI.createView(AnkoContext.create(ctx, this))
        (activity as AppCompatActivity).setSupportActionBar(forecastCardsFragmentUI.tb)
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

        forecastCardsFragmentUI.tv.text = mWeather?.city?.name

        /*async {
            val weather = awaitSuccessful(App.openWeather.getForecast(625144))
            forecastCardsFragmentUI.tv.text = weather.city.name
        }.onError {
            val errorMessage = getErrorMessage(it.cause!!)
            forecastCardsFragmentUI.tv.text = errorMessage
        }*/
    }
}