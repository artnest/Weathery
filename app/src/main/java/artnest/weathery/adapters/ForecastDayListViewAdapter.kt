package artnest.weathery.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import artnest.weathery.R
import artnest.weathery.controller.activities.ForecastDayActivity
import artnest.weathery.helpers.Common
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*

class ForecastDayListViewAdapter(fact: ForecastDayActivity) : BaseAdapter() {

    val weatherHours = fact.mWeatherHoursList

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val item = getItem(position)

        return with(parent!!.context) {
            relativeLayout {
                val icon = imageView {
                    id = R.id.icon_weather
                    Picasso.with(ctx).load(Common.getImage(item.weather[0].icon)).into(this)
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
