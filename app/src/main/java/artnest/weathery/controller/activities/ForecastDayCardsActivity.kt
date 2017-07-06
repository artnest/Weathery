package artnest.weathery.controller.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import artnest.weathery.helpers.Common
import artnest.weathery.model.data.Cities
import artnest.weathery.model.data.WeatheryPrefs
import artnest.weathery.model.gson.WeatherForecastElement
import artnest.weathery.view.ForecastDayCardsActivityUI
import org.jetbrains.anko.setContentView

class ForecastDayCardsActivity : AppCompatActivity() {

    lateinit var forecastDayCardsActivityUI: ForecastDayCardsActivityUI
    lateinit var mWeatherHoursList: List<WeatherForecastElement>

    companion object {
        val WEATHER_HOURS_DATA = "weather_hours_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mWeatherHoursList = intent.getParcelableArrayListExtra<WeatherForecastElement>(WEATHER_HOURS_DATA)

        forecastDayCardsActivityUI = ForecastDayCardsActivityUI()
        forecastDayCardsActivityUI.setContentView(this)

        setSupportActionBar(forecastDayCardsActivityUI.tb)
        supportActionBar!!.title = Cities.values()[WeatheryPrefs.selectedCity].name + ", " +
                Common.getDate(mWeatherHoursList[0].dtTxt)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}
