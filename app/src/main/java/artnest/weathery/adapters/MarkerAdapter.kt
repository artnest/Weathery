package artnest.weathery.adapters

import android.content.Context
import android.view.View
import artnest.weathery.controller.fragments.MapFragment
import artnest.weathery.helpers.loadUrl
import artnest.weathery.helpers.toWeatherInfo
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.model.Marker
import org.jetbrains.anko.imageView
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.textView

class MarkerAdapter(val ctx: Context) : InfoWindowAdapter {
    override fun getInfoWindow(marker: Marker) = null

    override fun getInfoContents(marker: Marker): View? {
        val item = MapFragment.mWeather.toWeatherInfo()

        return with(ctx) {
            linearLayout {
                imageView {
                    loadUrl(item.icon)
                }

                textView {
                    text = item.name
                }
            }
        }
    }
}
