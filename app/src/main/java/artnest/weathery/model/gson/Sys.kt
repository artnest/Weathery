package artnest.weathery.model.gson

import com.google.gson.annotations.SerializedName

data class Sys(
        @SerializedName("pod")
        val pod: String
)