package artnest.weathery.model.gson

import com.google.gson.annotations.SerializedName

data class Snow(
        @SerializedName("3h")
        val _3h: Double
)
