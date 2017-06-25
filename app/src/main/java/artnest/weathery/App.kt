package artnest.weathery

import android.app.Application
import artnest.weathery.helpers.OpenWeather
import artnest.weathery.helpers.OpenWeatherProvider

class App : Application() {

    companion object {
        lateinit var openWeather: OpenWeather
    }

    override fun onCreate() {
        super.onCreate()

        openWeather = OpenWeatherProvider.provideOpenWeather()
    }
}
