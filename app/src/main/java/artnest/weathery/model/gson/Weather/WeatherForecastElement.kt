package artnest.weathery.model.gson.Weather

import com.google.gson.annotations.SerializedName
import io.mironov.smuggler.AutoParcelable

data class WeatherForecastElement(
        @SerializedName("dt")
        val dt: Long,
        @SerializedName("main")
        val main: Main,
        @SerializedName("weather")
        val weather: List<Weather> = arrayListOf<Weather>(),
        @SerializedName("clouds")
        val clouds: Clouds,
        @SerializedName("wind")
        val wind: Wind,
        @SerializedName("rain")
        val rain: Rain?,
        @SerializedName("snow")
        val snow: Snow?,
        @SerializedName("sys")
        val sys: Sys,
        @SerializedName("dt_txt")
        val dtTxt: String
) : AutoParcelable
