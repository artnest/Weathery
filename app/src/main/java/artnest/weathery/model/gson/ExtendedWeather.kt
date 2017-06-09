package artnest.weathery.model.gson

import com.google.gson.annotations.SerializedName

data class ExtendedWeather(
        @SerializedName("city")
        val city: City,
        @SerializedName("cod")
        val cod: String,
        @SerializedName("message")
        val message: Double,
        @SerializedName("cnt")
        val cnt: Int,
        @SerializedName("list")
        val weatherForecastElement: List<WeatherForecastElement> = arrayListOf<WeatherForecastElement>()
)
