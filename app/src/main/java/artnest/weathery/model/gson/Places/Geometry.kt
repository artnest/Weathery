package artnest.weathery.model.gson.Places

import com.google.gson.annotations.SerializedName

data class Geometry(
        @SerializedName("location")
        val location: Location
)
