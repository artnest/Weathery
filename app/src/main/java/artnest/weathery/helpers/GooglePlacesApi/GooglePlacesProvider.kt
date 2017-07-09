package artnest.weathery.helpers.GooglePlacesApi

object GooglePlacesProvider {
    fun provideGooglePlaces() = GooglePlaces(GooglePlacesApiService.create())
}
