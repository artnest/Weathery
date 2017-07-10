package artnest.weathery.adapters

import android.graphics.Typeface
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import artnest.weathery.R
import artnest.weathery.controller.activities.ForecastDayActivity
import artnest.weathery.helpers.loadUrl
import artnest.weathery.helpers.toWeatherInfoDetails
import org.jetbrains.anko.*

class ForecastDayAdapter(fact: ForecastDayActivity) : BaseAdapter() {

    val weatherHours = fact.mWeatherHoursList

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val item = getItem(position).toWeatherInfoDetails()

        return with(parent!!.context) {
            relativeLayout {
                padding = dip(10)

                relativeLayout {
                    id = R.id.card_initial_data

                    textView {
                        text = item.dt
                        textSize = 14f
                    }.lparams {
                        alignParentLeft()
                        centerVertically()
                        leftMargin = dip(8)
                    }

                    imageView {
                        loadUrl(item.icon)
                    }.lparams {
                        width = dip(64)
                        height = dip(64)
                        alignParentRight()
                        rightMargin = dip(8)
                    }
                }.lparams {
                    width = matchParent
                    height = wrapContent
                }

                textView {
                    id = R.id.card_temperature
                    text = item.temp
                    textSize = 14f
                }.lparams {
                    alignParentLeft()
                    bottomOf(R.id.card_initial_data)
                }

                textView {
                    id = R.id.card_description
                    text = item.desc
                    textSize = 14f
                    setTypeface(typeface, Typeface.ITALIC)
                    ellipsize = TextUtils.TruncateAt.END
                    maxLines = 1
                }.lparams {
                    alignParentLeft()
                    bottomOf(R.id.card_temperature)
                }

                textView {
                    id = R.id.card_wind
                    text = item.wind
                    textSize = 14f
                }.lparams {
                    alignParentLeft()
                    bottomOf(R.id.card_description)
                }

                textView {
                    id = R.id.card_clouds
                    text = item.clouds
                    textSize = 14f
                }.lparams {
                    alignParentLeft()
                    bottomOf(R.id.card_wind)
                }

                textView {
                    id = R.id.card_pressure
                    text = item.pressure
                    textSize = 14f
                }.lparams {
                    alignParentLeft()
                    bottomOf(R.id.card_clouds)
                }

                textView {
                    id = R.id.card_humidity
                    text = item.humidity
                    textSize = 14f
                }.lparams {
                    alignParentLeft()
                    bottomOf(R.id.card_pressure)
                }

                textView {
                    id = R.id.card_rain
                    text = item.rain
                    textSize = 14f
                }.lparams {
                    alignParentLeft()
                    bottomOf(R.id.card_humidity)
                }

                textView {
                    id = R.id.card_snow
                    text = item.snow
                    textSize = 14f
                }.lparams {
                    alignParentLeft()
                    bottomOf(R.id.card_rain)
                }
            }
        }
    }

    override fun getItem(position: Int) = weatherHours[position]

    override fun getItemId(position: Int) = weatherHours.indexOf(getItem(position)).toLong()

    override fun getCount() = weatherHours.size
}
