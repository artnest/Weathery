package artnest.weathery.controller.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import artnest.weathery.model.data.Cities
import artnest.weathery.model.data.WeatheryPrefs
import artnest.weathery.model.gson.WeatherForecastElement
import artnest.weathery.view.ForecastDayActivityUI
import org.jetbrains.anko.setContentView

class ForecastDayActivity : AppCompatActivity() {

    lateinit var forecastDayActivityUI: ForecastDayActivityUI
    lateinit var mWeatherHoursList: List<WeatherForecastElement>

    companion object {
        val WEATHER_HOURS_DATA = "weather_hours_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mWeatherHoursList = intent.getParcelableArrayListExtra<WeatherForecastElement>(WEATHER_HOURS_DATA)

        forecastDayActivityUI = ForecastDayActivityUI()
        forecastDayActivityUI.setContentView(this)

        setSupportActionBar(forecastDayActivityUI.tb)
        supportActionBar!!.title = Cities.values()[WeatheryPrefs.selectedCity].name + ", " +
                mWeatherHoursList[0].dtTxt.substringBefore(" ")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}
