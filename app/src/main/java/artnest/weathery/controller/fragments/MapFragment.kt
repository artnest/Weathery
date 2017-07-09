package artnest.weathery.controller.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import artnest.weathery.R
import artnest.weathery.adapters.MarkerAdapter
import artnest.weathery.helpers.Common
import artnest.weathery.helpers.inflate
import artnest.weathery.model.data.Cities
import artnest.weathery.model.gson.Weather.CurrentWeather
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.map_fragment.view.*
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast

class MapFragment : Fragment(), OnMapReadyCallback {

    lateinit var mMapView: MapView
    private lateinit var mMap: GoogleMap
    private val markers = mutableMapOf<String, LatLng>()

    lateinit var mWeather: CurrentWeather
    var mBitmap: Bitmap? = null

    val REQUEST_IMAGE_CAPTURE = 1

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

        val markerAdapter = MarkerAdapter(this)
        mMap.setOnMarkerClickListener(markerAdapter)
        mMap.setInfoWindowAdapter(markerAdapter)
        mMap.setOnInfoWindowClickListener(markerAdapter)
        mMap.setOnInfoWindowLongClickListener(markerAdapter)
        mMap.setOnInfoWindowCloseListener(markerAdapter)
        markers.entries.forEach { e ->
            mMap.addMarker(MarkerOptions().position(e.value).title(e.key))
        }

        // mMap.animateCamera(CameraUpdateFactory.newLatLng(markers[Cities.values()[WeatheryPrefs.selectedCity].name]))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_IMAGE_CAPTURE -> {
                if (resultCode == RESULT_OK) {
                    val storageDir = Common.getPicturesStorageDir(ctx)
                    if (storageDir != null) {
                        val photo = Common.getLastModifiedFile(storageDir)
                        if (photo != null) {
                            Common.saveScaledBitmap(photo.absolutePath)
                        }
                        toast("Photo was saved")
                    }
                }
            }
        }
    }
}
