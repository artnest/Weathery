package artnest.weathery.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import artnest.weathery.App
import artnest.weathery.controller.fragments.ForecastListFragment
import artnest.weathery.controller.fragments.ForecastParentFragment
import artnest.weathery.model.data.Cities
import artnest.weathery.model.data.WeatheryPrefs
import artnest.weathery.model.gson.WeatherForecastElement
import co.metalab.asyncawait.async
import co.metalab.asyncawait.awaitSuccessful
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout

class ListViewAdapter(val container: ForecastListFragment) : BaseAdapter() {

    var weatherDays = emptyList<List<WeatherForecastElement>>()
//    var list = weather.weatherForecastElement // TODO handle empty data list (null)

    init {
        reload()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val weatherDayList = getItem(position)
        val item = weatherDayList[weatherDayList.size / 2]

        return with(parent!!.context) {
            verticalLayout {

                textView {
                    text = "Temperature: ${item.dtTxt}"
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
            }
        }
    }

    override fun getItem(position: Int) = weatherDays[position]

    override fun getItemId(position: Int) = weatherDays.indexOf(getItem(position)).toLong()

    override fun getCount() = weatherDays.size

    fun reload() {
        getWeatherData()
    }

    private fun getWeatherData() {
        async {
            val list = awaitSuccessful(App.openWeather
                    .getForecast(Cities.values()[WeatheryPrefs.selectedCity].id))
                    .weatherForecastElement

            weatherDays = (container.parentFragment as ForecastParentFragment).getWeatherDays(list)
            container.forecastListFragmentUI.tb.title = Cities.values()[WeatheryPrefs.selectedCity].name
            notifyDataSetChanged()
        }.onError {
            container.toast(ForecastParentFragment.getErrorMessage(it.cause!!))
        }
    }
}
