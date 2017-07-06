package artnest.weathery.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import artnest.weathery.R
import artnest.weathery.controller.activities.ForecastDayActivity
import artnest.weathery.helpers.Common
import artnest.weathery.helpers.loadUrl
import org.jetbrains.anko.*

class ForecastDayAdapter(fact: ForecastDayActivity) : BaseAdapter() {

    val weatherHours = fact.mWeatherHoursList

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val item = getItem(position)

        return with(parent!!.context) {
            relativeLayout {
                padding = dip(8)

                val icon = imageView {
                    id = R.id.icon_weather
                    loadUrl(Common.getImage(item.weather[0].icon))
                }.lparams {
                    width = dip(64)
                    height = dip(64)
                    centerVertically()
                }

                verticalLayout {
                    textView {
                        text = "Date: ${item.dtTxt.substringBefore(" ")}"
                    }

                    textView {
                        text = "Temperature: ${item.main.temp}"
                    }
                    textView {
                        text = "Min temp: ${item.main.tempMin}"
                    }
                    textView {
                        text = "Max temp: ${item.main.tempMax}"
                    }
                }.lparams {
                    rightOf(icon)
                }
            }
        }
    }

    override fun getItem(position: Int) = weatherHours[position]

    override fun getItemId(position: Int) = weatherHours.indexOf(getItem(position)).toLong()

    override fun getCount() = weatherHours.size
}
