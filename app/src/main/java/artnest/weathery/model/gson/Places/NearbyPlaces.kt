package artnest.weathery.model.gson.Places

import com.google.gson.annotations.SerializedName

data class NearbyPlaces(
        @SerializedName("html_attributions")
        val htmlAttributions: List<String> = arrayListOf<String>(),
        @SerializedName("next_page_token")
        val nextPageToken: String?,
        @SerializedName("results")
        val results: List<PlaceInfo> = arrayListOf<PlaceInfo>(),
        @SerializedName("status")
        val status: String
)
