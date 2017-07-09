package artnest.weathery.model.gson.Weather

import com.google.gson.annotations.SerializedName
import io.mironov.smuggler.AutoParcelable

data class Rain(
        @SerializedName("3h")
        val _3h: Double
) : AutoParcelable
