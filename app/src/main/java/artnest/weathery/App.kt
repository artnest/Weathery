package artnest.weathery

import android.app.Application
import artnest.weathery.helpers.OpenWeather
import artnest.weathery.helpers.OpenWeatherProvider

class App : Application() {

    companion object {
        val openWeather: OpenWeather = OpenWeatherProvider.provideOpenWeather()
    }
}
