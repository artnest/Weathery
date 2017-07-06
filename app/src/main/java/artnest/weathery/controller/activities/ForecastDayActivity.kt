package artnest.weathery.controller.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import artnest.weathery.helpers.Common
import artnest.weathery.model.gson.WeatherForecastElement
import artnest.weathery.view.ForecastDayActivityUI
import org.jetbrains.anko.setContentView

class ForecastDayActivity : AppCompatActivity() {

    lateinit var forecastDayActivityUI: ForecastDayActivityUI
    lateinit var mCityName: String
    lateinit var mWeatherHoursList: List<WeatherForecastElement>

    companion object {
        val CITY_NAME = "city_name"
        val WEATHER_HOURS_DATA = "weather_hours_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mCityName = intent.getStringExtra(CITY_NAME)
        mWeatherHoursList = intent.getParcelableArrayListExtra<WeatherForecastElement>(WEATHER_HOURS_DATA)

        forecastDayActivityUI = ForecastDayActivityUI()
        forecastDayActivityUI.setContentView(this)

        setSupportActionBar(forecastDayActivityUI.tb)
        supportActionBar!!.title = mCityName + ", " + Common.getDate(mWeatherHoursList[0].dtTxt)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}
