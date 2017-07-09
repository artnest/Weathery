package artnest.weathery.model.gson.Weather

import com.google.gson.annotations.SerializedName
import io.mironov.smuggler.AutoParcelable

data class Coord(
        @SerializedName("lon")
        val lon: Double,
        @SerializedName("lat")
        val lat: Double
) : AutoParcelable