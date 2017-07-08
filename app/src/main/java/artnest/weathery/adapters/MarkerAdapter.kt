package artnest.weathery.adapters

import android.content.Context
import android.graphics.Typeface
import android.view.View
import artnest.weathery.R
import artnest.weathery.controller.fragments.MapFragment
import artnest.weathery.helpers.loadUrl
import artnest.weathery.helpers.toWeatherInfo
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.model.Marker
import org.jetbrains.anko.*

class MarkerAdapter(val ctx: Context) : InfoWindowAdapter {
    override fun getInfoWindow(marker: Marker) = null

    override fun getInfoContents(marker: Marker): View? {
        val item = MapFragment.mWeather.toWeatherInfo()

        return with(ctx) {
            relativeLayout {
                padding = dip(8)

                textView {
                    id = R.id.info_city_name
                    text = item.name
                    textSize = 16f
                    setTypeface(typeface, Typeface.BOLD)
                }.lparams {
                    centerHorizontally()
                }

                textView {
                    id = R.id.info_date
                    text = "Last updated: ${item.dt}"
                    textSize = 12f
                    setTypeface(typeface, Typeface.BOLD)
                }.lparams {
                    centerHorizontally()
                    bottomOf(R.id.info_city_name)
                    bottomMargin = dip(4)
                }

                relativeLayout {
                    id = R.id.info_main

                    imageView {
                        id = R.id.info_icon
                        loadUrl(item.icon)
                    }.lparams {
                        width = dip(64)
                        height = dip(64)
                        centerVertically()
                        rightMargin = dip(8)
                    }

                    textView {
                        id = R.id.info_description
                        text = item.desc
                        textSize = 14f
                        setTypeface(typeface, Typeface.ITALIC)
                    }.lparams {
                        rightOf(R.id.info_icon)
                        rightMargin = dip(16)
                    }

                    textView {
                        id = R.id.info_temperature
                        text = item.temp
                        textSize = 14f
                    }.lparams {
                        rightOf(R.id.info_description)
                    }

                    textView {
                        id = R.id.info_sunrise
                        text = item.sunrise
                        textSize = 14f
                    }.lparams {
                        bottomOf(R.id.info_description)
                        rightOf(R.id.info_icon)
                        rightMargin = dip(16)
                    }

                    textView {
                        id = R.id.info_sunset
                        text = item.sunset
                        textSize = 14f
                    }.lparams {
                        bottomOf(R.id.info_sunrise)
                        rightOf(R.id.info_icon)
                    }
                }.lparams {
                    bottomOf(R.id.info_date)
                }

                textView {
                    id = R.id.info_wind
                    text = item.wind
                    textSize = 14f
                }.lparams {
                    bottomOf(R.id.info_main)
                    rightMargin = dip(24)
                }

                textView {
                    id = R.id.info_clouds
                    text = item.clouds
                    textSize = 14f
                }.lparams {
                    bottomOf(R.id.info_main)
                    rightOf(R.id.info_wind)
                }

                textView {
                    id = R.id.info_pressure
                    text = item.pressure
                    textSize = 14f
                }.lparams {
                    bottomOf(R.id.info_wind)
                }

                textView {
                    id = R.id.info_humidity
                    text = item.humidity
                    textSize = 14f
                }.lparams {
                    bottomOf(R.id.info_pressure)
                }

                textView {
                    id = R.id.info_rain
                    text = item.rain
                    textSize = 14f
                }.lparams {
                    bottomOf(R.id.info_humidity)
                }

                textView {
                    id = R.id.info_snow
                    text = item.snow
                    textSize = 14f
                }.lparams {
                    bottomOf(R.id.info_rain)
                }
            }
        }
    }
}
