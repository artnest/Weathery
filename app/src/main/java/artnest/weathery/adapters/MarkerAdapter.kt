package artnest.weathery.adapters

import android.content.Context
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.model.Marker
import org.jetbrains.anko.linearLayout

class MarkerAdapter(val ctx: Context) : InfoWindowAdapter {
    override fun getInfoWindow(marker: Marker) = null

    override fun getInfoContents(marker: Marker) = with(ctx) {
        linearLayout {
        }
    }
}
