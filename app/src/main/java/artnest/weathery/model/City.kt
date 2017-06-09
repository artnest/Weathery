package artnest.weathery.model

import com.google.gson.annotations.SerializedName
import kotlin.collections.List

data class City(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("coord")
        val coord: Coord,
        @SerializedName("cod")
        val cod: String,
        @SerializedName("message")
        val message: Double,
        @SerializedName("cnt")
        val cnd: Int,
        @SerializedName("list")
        val list: List<artnest.weathery.model.List>
)
