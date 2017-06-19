package artnest.weathery.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import artnest.weathery.App
import artnest.weathery.controller.fragments.ForecastListFragment
import artnest.weathery.model.data.Cities
import artnest.weathery.model.data.WeatheryPrefs
import artnest.weathery.model.gson.WeatherForecastElement
import co.metalab.asyncawait.async
import co.metalab.asyncawait.awaitSuccessful
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.textView

//class ListViewAdapter(val container: ForecastParentFragment) : BaseAdapter() {
class ListViewAdapter(val container: ForecastListFragment) : BaseAdapter() {
//class ListViewAdapter(val weather: ExtendedWeather) : BaseAdapter() {

    // TODO try to get data from parentFragment in async block in init()

    //    var list = container.getWeatherData()!!.weatherForecastElement // TODO get data async ?
    var list: List<WeatherForecastElement> = emptyList() // TODO get data async ?
//    var list = weather.weatherForecastElement // TODO handle empty data list (null)

    init {
        getWeatherData()
    }

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

    fun getWeatherData() {
        async {
            list = awaitSuccessful(App.openWeather
                    .getForecast(Cities.values()[WeatheryPrefs.selectedCity].id))
                    .weatherForecastElement
            notifyDataSetChanged()
        }.onError {
//            container.toast(container.getErrorMessage(it.cause!!))
            container.toast("Error")
        }
    }
}