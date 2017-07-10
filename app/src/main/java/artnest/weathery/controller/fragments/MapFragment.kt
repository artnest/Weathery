package artnest.weathery.controller.fragments

import android.Manifest
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
import artnest.weathery.controller.activities.ForecastActivity
import artnest.weathery.helpers.Common
import artnest.weathery.helpers.inflate
import artnest.weathery.model.data.Cities
import artnest.weathery.model.gson.Weather.CurrentWeather
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.map_fragment.view.*
import org.jetbrains.anko.okButton
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast

class MapFragment : Fragment(), OnMapReadyCallback {

    lateinit var mMapView: MapView
    private lateinit var mMap: GoogleMap
    private val markers = mutableMapOf<String, LatLng>()

    lateinit var mWeather: CurrentWeather
    var mBitmap: Bitmap? = null

    val REQUEST_IMAGE_CAPTURE = 1

    lateinit var permissionListener: PermissionListener

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

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        permissionListener = object : PermissionListener {
            override fun onPermissionGranted(response: PermissionGrantedResponse) {
                mMap.isMyLocationEnabled = true
                mMap.uiSettings.isMyLocationButtonEnabled = true
                getDeviceLocation()
            }

            override fun onPermissionDenied(response: PermissionDeniedResponse) {
                // toast("Location permission denied")
            }

            override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest,
                                                            token: PermissionToken) {
                alert {
                    title = getString(R.string.location_permission)
                    message = getString(R.string.location_permission_message_rationale)

                    okButton {
                    }
                }.show()
                token.cancelPermissionRequest()
            }
        }
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
        mMap.uiSettings.isZoomControlsEnabled = true

        val markerAdapter = MarkerAdapter(this)
        mMap.setOnMarkerClickListener(markerAdapter)
        mMap.setInfoWindowAdapter(markerAdapter)
        mMap.setOnInfoWindowClickListener(markerAdapter)
        mMap.setOnInfoWindowLongClickListener(markerAdapter)
        mMap.setOnInfoWindowCloseListener(markerAdapter)

        markers.entries.forEach { e ->
            mMap.addMarker(MarkerOptions().position(e.value).title(e.key))
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(markers[Cities.Minsk.name]))

        Dexter.withActivity(act)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(permissionListener)
                .check()
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

    fun getDeviceLocation() {
        val mLastKnownLocation = LocationServices.FusedLocationApi
                .getLastLocation((act as ForecastActivity).googleApiClient)

        if (mLastKnownLocation != null) {
            val deviceLocation = LatLng(mLastKnownLocation.latitude, mLastKnownLocation.longitude)
            mMap.moveCamera(CameraUpdateFactory.newLatLng(deviceLocation))
        } else {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(markers[Cities.Minsk.name]))
            mMap.uiSettings.isMyLocationButtonEnabled = false
        }
    }
}
