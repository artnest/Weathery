package artnest.weathery.model.gson

import com.google.gson.annotations.SerializedName

data class Rain(
        @SerializedName("3h")
        val _3h: Double
)
