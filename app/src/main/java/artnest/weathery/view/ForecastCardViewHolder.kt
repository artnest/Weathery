package artnest.weathery.view

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import artnest.weathery.R
import artnest.weathery.helpers.loadUrl
import artnest.weathery.model.data.WeatherInfo
import artnest.weathery.model.gson.WeatherForecastElement
import org.jetbrains.anko.find

class ForecastCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val icon: ImageView = itemView.find(R.id.card_icon)
    val date: TextView = itemView.find(R.id.card_date)
    val desc: TextView = itemView.find(R.id.card_description)
    val temp: TextView = itemView.find(R.id.card_temperature)
    val clouds: TextView = itemView.find(R.id.card_clouds)
    val pressure: TextView = itemView.find(R.id.card_pressure)

    fun bind(item: WeatherInfo, itemList: List<WeatherForecastElement>,
             listener: (List<WeatherForecastElement>) -> Unit) {
        icon.loadUrl(item.icon)
        date.text = item.dt
        desc.text = item.desc
        temp.text = item.temp
        clouds.text = item.clouds
        pressure.text = item.pressure

        itemView.setOnClickListener {
            listener(itemList)
        }
    }
}
