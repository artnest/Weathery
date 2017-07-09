package artnest.weathery.helpers.GooglePlacesApi

import artnest.weathery.model.gson.Places.NearbyPlaces
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface GooglePlacesApiService {

    @GET("json?")
    fun nearbyPlaces(@Query("location") location: String,
                     @Query("radius") radius: Int = 500,
                     @Query("key") key: String): Call<NearbyPlaces>

    companion object Factory {
        val API_KEY = "AIzaSyDZAtFaNBA8V8YCWloLXq__APGXyui12Ss"

        fun create(): GooglePlacesApiService {
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://maps.googleapis.com/maps/api/place/nearbysearch/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return retrofit.create(GooglePlacesApiService::class.java)
        }
    }
}
