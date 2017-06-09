package artnest.weathery.helpers

object OpenWeatherProvider {
    fun provideOpenWeather(): OpenWeather {
        return OpenWeather(OpenWeatherApiService.create())
    }
}