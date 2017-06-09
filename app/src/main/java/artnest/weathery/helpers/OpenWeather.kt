package artnest.weathery.helpers

class OpenWeather(val apiService: OpenWeatherApiService) {
    fun getForecast(id: Int) = apiService.forecast(id, OpenWeatherApiService.API_KEY)
}