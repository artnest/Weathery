package artnest.weathery.adapters

import android.content.Context
import artnest.weathery.controller.fragments.MapFragment
import artnest.weathery.helpers.Common
import artnest.weathery.helpers.loadUrl
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.model.Marker
import org.jetbrains.anko.imageView
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.textView

class MarkerAdapter(val ctx: Context) : InfoWindowAdapter {
    override fun getInfoWindow(marker: Marker) = null

    override fun getInfoContents(marker: Marker) = with(ctx) {
        with(MapFragment.mWeather) {
            linearLayout {
                imageView {
                    loadUrl(Common.getImage(weather[0].icon))
                }

                textView {
                    text = name
                }
            }
        }
    }
}
