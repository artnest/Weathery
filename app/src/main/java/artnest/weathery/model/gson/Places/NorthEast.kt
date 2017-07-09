package artnest.weathery.model.gson.Places

import com.google.gson.annotations.SerializedName

data class NorthEast(
        @SerializedName("lat")
        val lat: Double,
        @SerializedName("lng")
        val lng: Double
)
