package artnest.weathery.model

import com.google.gson.annotations.SerializedName

data class Data(
        @SerializedName("city")
        val city: City
)