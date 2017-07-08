package artnest.weathery.adapters

import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import artnest.weathery.App
import artnest.weathery.R
import artnest.weathery.controller.fragments.ForecastListFragment
import artnest.weathery.controller.fragments.ForecastParentFragment
import artnest.weathery.helpers.Common
import artnest.weathery.helpers.loadUrl
import artnest.weathery.helpers.toWeatherInfo
import artnest.weathery.model.data.Cities
import artnest.weathery.model.data.WeatheryPrefs
import artnest.weathery.model.gson.WeatherForecastElement
import co.metalab.asyncawait.async
import co.metalab.asyncawait.awaitSuccessful
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.toast

class ForecastListViewAdapter(val fr: ForecastListFragment) : BaseAdapter() {

    var weatherDays = emptyList<List<WeatherForecastElement>>()

    init {
        if (fr.mWeather != null) {
            weatherDays = (fr.parentFragment as ForecastParentFragment)
                    .getWeatherDays(fr.mWeather!!.weatherForecastElement)
            fr.forecastListFragmentUI.tb.title = fr.mWeather!!.city.name
            notifyDataSetChanged()
        } else {
            reload()
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val weatherDayList = getItem(position)
        val item = weatherDayList[weatherDayList.size / 2].toWeatherInfo()

        return with(parent!!.context) {
            relativeLayout {
                padding = dip(8)

                imageView {
                    id = R.id.list_icon
                    loadUrl(item.icon)
                }.lparams {
                    width = dip(64)
                    height = dip(64)
                    centerVertically()
                    rightMargin = dip(16)
                }

                textView {
                    id = R.id.list_date
                    text = item.dt
                    setTypeface(typeface, Typeface.BOLD)
                }.lparams {
                    alignParentTop()
                    rightMargin = dip(16)
                    rightOf(R.id.list_icon)
                }

                textView {
                    id = R.id.list_desc
                    text = item.desc
                    setTypeface(typeface, Typeface.ITALIC)
                }.lparams {
                    rightOf(R.id.list_date)
                }

                textView {
                    id = R.id.list_temperature
                    text = item.temp
                }.lparams {
                    bottomOf(R.id.list_date)
                    rightOf(R.id.list_icon)
                }

                textView {
                    id = R.id.list_clouds
                    text = item.clouds
                }.lparams {
                    bottomOf(R.id.list_temperature)
                    rightMargin = dip(16)
                    rightOf(R.id.list_icon)
                }

                textView {
                    id = R.id.list_pressure
                    text = item.pressure
                }.lparams {
                    bottomOf(R.id.list_temperature)
                    rightOf(R.id.list_clouds)
                }
            }.applyRecursively { view ->
                when (view) {
                    is TextView -> view.textSize = 14f
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
            val weather = awaitSuccessful(App.openWeather
                    .getForecast(Cities.values()[WeatheryPrefs.selectedCity].id))
            (fr.parentFragment as ForecastParentFragment).mWeatherData = weather

            weatherDays = (fr.parentFragment as ForecastParentFragment)
                    .getWeatherDays(weather.weatherForecastElement)
            fr.forecastListFragmentUI.tb.title = weather.city.name
            notifyDataSetChanged()
        }.onError {
            fr.toast(Common.getErrorMessage(it.cause!!))
        }
    }
}
