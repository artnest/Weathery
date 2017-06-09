package artnest.weathery.helpers

import artnest.weathery.model.City
import retrofit2.Call

class OpenWeather(val apiService: OpenWeatherApiService) {
    fun getForecast(id: Int): Call<City> {
        return apiService.forecast(id, OpenWeatherApiService.API_KEY)
    }
}