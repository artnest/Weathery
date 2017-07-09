package artnest.weathery

import android.app.Application
import artnest.weathery.helpers.GooglePlacesApi.GooglePlaces
import artnest.weathery.helpers.GooglePlacesApi.GooglePlacesProvider
import artnest.weathery.helpers.OpenWeatherApi.OpenWeather
import artnest.weathery.helpers.OpenWeatherApi.OpenWeatherProvider

class App : Application() {

    companion object {
        lateinit var openWeather: OpenWeather
        lateinit var googlePlaces: GooglePlaces
    }

    override fun onCreate() {
        super.onCreate()

        openWeather = OpenWeatherProvider.provideOpenWeather()
        googlePlaces = GooglePlacesProvider.provideGooglePlaces()
    }
}
