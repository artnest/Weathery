package artnest.weathery.controller.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import artnest.weathery.helpers.OpenWeatherErrorResponse
import artnest.weathery.model.gson.ExtendedWeather
import artnest.weathery.view.ForecastCardsFragmentUI
import co.metalab.asyncawait.RetrofitHttpError
import com.google.gson.Gson
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.ctx

class ForecastCardsFragment : Fragment() {

    private lateinit var forecastCardsFragmentUI: ForecastCardsFragmentUI

    private var mWeather: ExtendedWeather? = null

    companion object {
        private val WEATHER_DATA = "weather_data"

        fun newInstance(weather: ExtendedWeather?): ForecastCardsFragment {
            return ForecastCardsFragment().apply {
                arguments = Bundle(1).apply {
                    putParcelable(WEATHER_DATA, weather)
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

        /*if (arguments != null) {
            mWeather = arguments.getParcelable<ExtendedWeather>(WEATHER_DATA)
        }*/
        mWeather = arguments.getParcelable<ExtendedWeather>(WEATHER_DATA)
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

    private fun getErrorMessage(it: Throwable) =
            if (it is RetrofitHttpError) {
                val httpErrorCode = it.errorResponse.code()
                val errorResponse = Gson().fromJson(it.errorResponse.body().toString(),
                        OpenWeatherErrorResponse::class.java)
                "[$httpErrorCode] ${errorResponse.message}"
            } else {
                "Couldn't load forecast (${it.message})"
            }
}