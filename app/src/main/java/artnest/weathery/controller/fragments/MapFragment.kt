package artnest.weathery.controller.fragments

import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import artnest.weathery.R
import artnest.weathery.helpers.inflate
import artnest.weathery.model.data.Cities
import artnest.weathery.model.data.WeatheryPrefs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.map_fragment.view.*
import org.jetbrains.anko.support.v4.act

class MapFragment : Fragment() {

    lateinit var mMapView: MapView
    private lateinit var mMap: GoogleMap
    private val markers = mutableMapOf<String, LatLng>()

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

        mMapView.getMapAsync { googleMap ->
            run {
                mMap = googleMap
                mMap.isMyLocationEnabled = true

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    markers.forEach { name, pos ->
                        mMap.addMarker(MarkerOptions().position(pos).title(name))
                    }
                } else {
                    markers.entries.forEach { e ->
                        mMap.addMarker(MarkerOptions().position(e.value).title(e.key))
                    }
                }
                mMap.moveCamera(CameraUpdateFactory.newLatLng(markers[Cities.values()[WeatheryPrefs.selectedCity].name]))
            }
        }

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
}
