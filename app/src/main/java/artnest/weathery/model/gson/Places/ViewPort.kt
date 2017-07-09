package artnest.weathery.model.gson.Places

import com.google.gson.annotations.SerializedName

data class ViewPort(
        @SerializedName("northeast")
        val northeast: NorthEast,
        @SerializedName("southwest")
        val southwest: SouthWest
)
