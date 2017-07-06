package artnest.weathery.view

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import artnest.weathery.R
import artnest.weathery.helpers.loadUrl
import artnest.weathery.model.data.WeatherDetails
import org.jetbrains.anko.find

class ForecastDetailsCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val date: TextView = itemView.find(R.id.card_date)
    val icon: ImageView = itemView.find(R.id.card_icon)
    val desc: TextView = itemView.find(R.id.card_description)
    val temp: TextView = itemView.find(R.id.card_temperature)
    val wind: TextView = itemView.find(R.id.card_wind)
    val clouds: TextView = itemView.find(R.id.card_clouds)
    val pressure: TextView = itemView.find(R.id.card_pressure)
    val humidity: TextView = itemView.find(R.id.card_humidity)
    val rain: TextView = itemView.find(R.id.card_rain)
    val snow: TextView = itemView.find(R.id.card_snow)

    fun bind(item: WeatherDetails) {
        date.text = item.dt
        icon.loadUrl(item.icon)
        desc.text = item.desc
        temp.text = item.temp
        wind.text = item.wind
        clouds.text = item.clouds
        pressure.text = item.pressure
        humidity.text = item.humidity
        rain.text = item.rain
        snow.text = item.snow
    }
}
