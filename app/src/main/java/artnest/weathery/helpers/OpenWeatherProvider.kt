package artnest.weathery.helpers

object OpenWeatherProvider {
    fun provideOpenWeather() = OpenWeather(OpenWeatherApiService.create())
}