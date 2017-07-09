package artnest.weathery.model.gson.Places

import com.google.gson.annotations.SerializedName

data class PlaceInfo(
        @SerializedName("geometry")
        val geometry: Geometry,
        @SerializedName("viewport")
        val viewport: ViewPort?,
        @SerializedName("icon")
        val icon: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("opening_hours")
        val openingHours: OpeningHours?,
        @SerializedName("photos")
        val photos: List<Photo> = arrayListOf<Photo>(),
        @SerializedName("place_id")
        val placeId: String,
        @SerializedName("scope")
        val scope: String? = "GOOGLE",
        @SerializedName("alt_ids")
        val altIds: List<AltId>?,
        @SerializedName("reference")
        val reference: String,
        @SerializedName("types")
        val types: List<String>,
        @SerializedName("vicinity")
        val vicinity: String?
)
