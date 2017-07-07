package artnest.weathery.adapters

import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import artnest.weathery.App
import artnest.weathery.R
import artnest.weathery.controller.fragments.ForecastListFragment
import artnest.weathery.controller.fragments.ForecastParentFragment
import artnest.weathery.helpers.Common
import artnest.weathery.helpers.loadUrl
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
        val item = weatherDayList[weatherDayList.size / 2]

        return with(parent!!.context) {
            relativeLayout {
                padding = dip(8)

                imageView {
                    id = R.id.icon_weather
                    loadUrl(Common.getImage(item.weather[0].icon))
                }.lparams {
                    width = dip(64)
                    height = dip(64)
                    centerVertically()
                }

                relativeLayout {
                    linearLayout {
                        id = R.id.forecast_main_data
                        orientation = LinearLayout.HORIZONTAL

                        textView {
                            text = Common.getDate(item.dtTxt)
                            setTypeface(typeface, Typeface.BOLD)
                        }

                        textView {
                            text = item.weather[0].description
                            setTypeface(typeface, Typeface.ITALIC)
                        }.lparams {
                            leftMargin = dip(16)
                        }
                    }

                    textView {
                        id = R.id.temperature_tv
                        text = Common.getTemperature(item.main.temp)
                    }.lparams {
                        bottomOf(R.id.forecast_main_data)
                    }

                    linearLayout {
                        id = R.id.forecast_clouds_data
                        orientation = LinearLayout.HORIZONTAL

                        textView {
                            text = Common.getClouds(item.clouds.all)
                        }

                        textView {
                            text = Common.getPressure(item.main.pressure)
                        }.lparams {
                            leftMargin = dip(16)
                        }
                    }.lparams {
                        bottomOf(R.id.temperature_tv)
                    }
                }.lparams {
                    leftMargin = dip(4)
                    rightOf(R.id.icon_weather)
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
