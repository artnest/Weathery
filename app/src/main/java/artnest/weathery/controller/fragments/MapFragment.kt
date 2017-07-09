package artnest.weathery.controller.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import artnest.weathery.App
import artnest.weathery.R
import artnest.weathery.adapters.MarkerAdapter
import artnest.weathery.controller.activities.ForecastActivity
import artnest.weathery.helpers.Common
import artnest.weathery.helpers.inflate
import artnest.weathery.model.data.Cities
import artnest.weathery.model.gson.Weather.CurrentWeather
import co.metalab.asyncawait.async
import co.metalab.asyncawait.awaitSuccessful
import com.google.android.gms.location.places.Places
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.map_fragment.view.*
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast

class MapFragment : Fragment(), OnMapReadyCallback, OnMarkerClickListener {

    lateinit var mMapView: MapView
    private lateinit var mMap: GoogleMap
    private val markers = mutableMapOf<String, LatLng>()

    lateinit var mWeather: CurrentWeather
    lateinit var mBitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Cities.values().toList().forEach { c ->
            markers.put(c.name, LatLng(c.lat, c.lon))
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val v = container!!.inflate(R.layout.map_fragment)
        mMapView = v.mapView

        mMapView.onCreate(savedInstanceState)
        mMapView.onResume()

        MapsInitializer.initialize(act.applicationContext)
        mMapView.getMapAsync(this)
        return v
    }

    override fun onResume() {
        super.onResume()
        mMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMapView.onLowMemory()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.isMyLocationEnabled = true  // TODO request permission

        mMap.setOnMarkerClickListener(this)
        mMap.setInfoWindowAdapter(MarkerAdapter(ctx, this))
        markers.entries.forEach { e ->
            mMap.addMarker(MarkerOptions().position(e.value).title(e.key))
        }

        // mMap.animateCamera(CameraUpdateFactory.newLatLng(markers[Cities.values()[WeatheryPrefs.selectedCity].name]))
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        async {
            val weather = awaitSuccessful(App.openWeather
                    .getCurrentForecast(Cities.valueOf(marker.title).id))
            mWeather = weather

            val places = awaitSuccessful(App.googlePlaces
                    .getNearbyPlaces(marker.position.latitude, marker.position.longitude))
            if (places.results.isNotEmpty()) {
                await {
                    val result = Places.GeoDataApi
                            .getPlacePhotos((act as ForecastActivity)
                                    .googleApiClient,
                                    places.results[0].placeId)
                            .await()

                    if (result != null && result.status.isSuccess) {
                        val photoMetadataBuffer = result.photoMetadata
                        if (photoMetadataBuffer.count > 0) {
                            val photo = photoMetadataBuffer[0]
                            mBitmap = photo.getPhoto((act as ForecastActivity).googleApiClient)
                                    .await()
                                    .bitmap
                        }
                        photoMetadataBuffer.release()
                    }
                }
            }

            marker.showInfoWindow()
        }.onError {
            toast(Common.getErrorMessage(it.cause!!))
        }
        return true
    }
}
