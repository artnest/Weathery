package artnest.weathery.helpers

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import artnest.weathery.model.data.CurrentWeatherInfo
import artnest.weathery.model.data.WeatherInfo
import artnest.weathery.model.data.WeatherInfoDetails
import artnest.weathery.model.gson.CurrentWeather
import artnest.weathery.model.gson.WeatherForecastElement
import com.squareup.picasso.Picasso

fun ViewGroup.inflate(layoutRes: Int) = LayoutInflater.from(context).inflate(layoutRes, this, false)
fun ImageView.loadUrl(url: String) = Picasso.with(context).load(url).into(this)

fun Double.format(digits: Int) = String.format("%.${digits}f", this)

fun WeatherForecastElement.toWeatherInfo() =
        WeatherInfo(
                Common.getImage(this.weather[0].icon),
                Common.getDate(this.dtTxt),
                this.weather[0].description,
                Common.getTemperature(this.main.temp),
                Common.getClouds(this.clouds.all),
                Common.getPressure(this.main.pressure)
        )

fun WeatherForecastElement.toWeatherInfoDetails() =
        WeatherInfoDetails(
                Common.getHours(this.dtTxt),
                Common.getImage(this.weather[0].icon),
                this.weather[0].description,
                Common.getTemperature(this.main.temp),
                Common.getWind(this.wind.speed),
                Common.getClouds(this.clouds.all),
                Common.getPressure(this.main.pressure),
                Common.getHumidity(this.main.humidity),
                Common.getRain(this.rain?._3h),
                Common.getSnow(this.snow?._3h)
        )

fun CurrentWeather.toWeatherInfo() =
        CurrentWeatherInfo(
                this.name,
                Common.unixTimestampToDateTime(this.dt),
                Common.getImage(this.weather[0].icon),
                this.weather[0].description,
                Common.unixTimestampToDateTime(this.sys.sunrise),
                Common.unixTimestampToDateTime(this.sys.sunset),
                Common.getTemperature(this.main.temp),
                Common.getWind(this.wind.speed),
                Common.getClouds(this.clouds.all),
                Common.getPressure(this.main.pressure),
                Common.getHumidity(this.main.humidity),
                Common.getRain(this.rain?._3h),
                Common.getSnow(this.snow?._3h)
        )
