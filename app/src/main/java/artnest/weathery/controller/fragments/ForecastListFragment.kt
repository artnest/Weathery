package artnest.weathery.controller.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import artnest.weathery.App
import artnest.weathery.helpers.OpenWeatherErrorResponse
import artnest.weathery.view.ForecastListFragmentUI
import co.metalab.asyncawait.RetrofitHttpError
import co.metalab.asyncawait.async
import co.metalab.asyncawait.awaitSuccessful
import com.google.gson.Gson
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.ctx

class ForecastListFragment : Fragment() {

    lateinit var mForecastListFragmentUI: ForecastListFragmentUI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mForecastListFragmentUI = ForecastListFragmentUI()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val v = mForecastListFragmentUI.createView(AnkoContext.create(ctx, this))
        (activity as AppCompatActivity).setSupportActionBar(mForecastListFragmentUI.tb)
        return v
    }

    override fun onResume() {
        super.onResume()

        async {
            val weather = awaitSuccessful(App.openWeather.getForecast(625144))
            mForecastListFragmentUI.tv.text = weather.city.name
        }.onError {
            val errorMessage = getErrorMessage(it.cause!!)
            mForecastListFragmentUI.tv.text = errorMessage
        }
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
