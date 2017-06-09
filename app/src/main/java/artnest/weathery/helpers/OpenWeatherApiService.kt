package artnest.weathery.helpers

import artnest.weathery.model.City
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApiService {

    @GET("/forecast")
    fun forecast(@Query("id") id: Int,
                 @Query("appid") appid: String): Call<City>

    companion object Factory {
        val API_KEY: String
            get() = "808cd9ff7e6852f10bf2d5301497096d"

        fun create(): OpenWeatherApiService {
            val retrofit = Retrofit.Builder()
                    .baseUrl("http://api.openweathermap.org/data/2.5/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return retrofit.create(OpenWeatherApiService::class.java)
        }
    }
}
