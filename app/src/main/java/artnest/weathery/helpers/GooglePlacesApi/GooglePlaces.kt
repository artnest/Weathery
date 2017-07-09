package artnest.weathery.helpers.GooglePlacesApi

import artnest.weathery.helpers.Common

class GooglePlaces(val apiService: GooglePlacesApiService) {
    fun getNearbyPlaces(lan: Double, lon: Double) =
            apiService.nearbyPlaces(
                    location = Common.getPlaceLocation(lan, lon),
                    key = GooglePlacesApiService.API_KEY
            )
}
