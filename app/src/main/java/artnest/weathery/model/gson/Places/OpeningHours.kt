package artnest.weathery.model.gson.Places

import com.google.gson.annotations.SerializedName

data class OpeningHours(
        @SerializedName("open_now")
        val openNow: Boolean
)
