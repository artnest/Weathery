package artnest.weathery.model.gson.Places

import com.google.gson.annotations.SerializedName

data class AltId(
        @SerializedName("place_id")
        val placeId: String,
        @SerializedName("scope")
        val scope: String
)
