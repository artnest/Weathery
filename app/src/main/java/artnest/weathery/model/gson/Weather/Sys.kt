package artnest.weathery.model.gson.Weather

import com.google.gson.annotations.SerializedName
import io.mironov.smuggler.AutoParcelable

data class Sys(
        @SerializedName("pod")
        val pod: String
) : AutoParcelable
