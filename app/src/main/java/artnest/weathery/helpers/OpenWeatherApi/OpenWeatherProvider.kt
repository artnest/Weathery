package artnest.weathery.helpers.OpenWeatherApi

object OpenWeatherProvider {
    fun provideOpenWeather() = OpenWeather(OpenWeatherApiService.create())
}
