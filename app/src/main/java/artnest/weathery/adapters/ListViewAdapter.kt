package artnest.weathery.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import artnest.weathery.model.gson.ExtendedWeather
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.textView

//class ListViewAdapter(val container: ForecastParentFragment) : BaseAdapter() {
class ListViewAdapter(val weather: ExtendedWeather) : BaseAdapter() {

    // TODO try to get data from parentFragment in async block in init()

//    var list = container.getWeatherData()!!.weatherForecastElement // TODO get data async ?
    var list = weather.weatherForecastElement // TODO handle empty data list (null)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val item = getItem(position)

        return with(parent!!.context) {
            linearLayout {
                orientation = LinearLayout.HORIZONTAL

                textView {
                    text = "Temperature: ${item.main.temp}"
                }
                textView {
                    text = "Min temp: ${item.main.tempMin}"
                }
                textView {
                    text = "Max temp: ${item.main.tempMax}"
                }
            }
        }
    }

    override fun getItem(position: Int) = list[position]

    override fun getItemId(position: Int) = getItem(position).dt.toLong()

    override fun getCount() = list.size
}