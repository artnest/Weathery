package artnest.weathery.model

import com.google.gson.annotations.SerializedName

data class List(
        @SerializedName("dt")
        val dt: Int,
        @SerializedName("main")
        val main: Main,
        @SerializedName("weather")
        val weather: kotlin.collections.List<Weather>,
        @SerializedName("clouds")
        val clouds: Clouds,
        @SerializedName("wind")
        val wind: Wind,
        @SerializedName("sys")
        val sys: Sys,
        @SerializedName("dtTxt")
        val dtTxt: String
)