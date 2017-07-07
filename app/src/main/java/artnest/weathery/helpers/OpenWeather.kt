package artnest.weathery.helpers

class OpenWeather(val apiService: OpenWeatherApiService) {
    fun getForecast(id: Int) = apiService.forecast(id, OpenWeatherApiService.API_KEY)
    fun getCurrentForecast(id: Int) = apiService.currentForecast(id, OpenWeatherApiService.API_KEY)
}