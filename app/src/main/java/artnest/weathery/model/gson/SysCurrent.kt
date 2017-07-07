package artnest.weathery.model.gson

import com.google.gson.annotations.SerializedName
import io.mironov.smuggler.AutoParcelable

data class SysCurrent(
        @SerializedName("type")
        val type: Int,
        @SerializedName("id")
        val id: Int,
        @SerializedName("message")
        val message: Double,
        @SerializedName("country")
        val country: String,
        @SerializedName("sunrise")
        val sunrise: Long,
        @SerializedName("sunset")
        val sunset: Long
) : AutoParcelable
