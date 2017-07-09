package artnest.weathery.model.gson.Places

import com.google.gson.annotations.SerializedName

data class Photo(
        @SerializedName("height")
        val height: Int,
        @SerializedName("html_attributions")
        val htmlAttributions: List<String> = arrayListOf<String>(),
        @SerializedName("photo_reference")
        val photoReference: String,
        @SerializedName("width")
        val width: Int
)
