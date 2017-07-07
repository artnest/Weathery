package artnest.weathery.model.gson

import com.google.gson.annotations.SerializedName
import io.mironov.smuggler.AutoParcelable

data class CurrentWeather(
        @SerializedName("coord")
        val coord: Coord,
        @SerializedName("weather")
        val weather: List<Weather> = arrayListOf<Weather>(),
        @SerializedName("base")
        val base: String,
        @SerializedName("main")
        val main: MainCurrent,
        @SerializedName("wind")
        val wind: Wind,
        @SerializedName("clouds")
        val clouds: Clouds,
        @SerializedName("rain")
        val rain: Rain?,
        @SerializedName("snow")
        val snow: Snow?,
        @SerializedName("dt")
        val dt: Int,
        @SerializedName("sys")
        val sys: SysCurrent,
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("cod")
        val cod: Int
) : AutoParcelable
