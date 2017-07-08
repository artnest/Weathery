package artnest.weathery.helpers

import co.metalab.asyncawait.RetrofitHttpError
import khronos.toDate
import khronos.toString
import java.text.SimpleDateFormat
import java.util.*

object Common {
    fun getImage(icon: String) = "http://openweathermap.org/img/w/$icon.png"
    fun getDate(rawDate: String) = rawDate.toDate("yyyy-MM-dd HH:mm:ss").toString("EEE d MMM")
    fun getHours(rawDate: String) = rawDate.toDate("yyyy-MM-dd HH:mm:ss").toString("HH:mm")
    fun getTemperature(temp: Double) = "$temp °C"
    fun getClouds(clouds: Int) = "clouds: $clouds %"
    fun getPressure(pressure: Double) = "$pressure hpa"
    fun getWind(speed: Double) = "$speed m/s"
    fun getHumidity(humidity: Int) = "humidity: $humidity %"
    fun getRain(rain: Double?) = "rain: ${rain?.format(4) ?: "no rain"}"
    fun getSnow(snow: Double?) = "snow: ${snow?.format(4) ?: "no snow"}"
    fun getSunrise(timestamp: Long) = "sunrise: ${Common.unixTimestampToDateTime(timestamp)}"
    fun getSunset(timestamp: Long) = "sunset: ${Common.unixTimestampToDateTime(timestamp)}"

    fun unixTimestampToDateTime(timestamp: Long): String {
        val d = Date()
        d.time = timestamp * 1000
        val f = SimpleDateFormat("HH:mm")
        f.timeZone = TimeZone.getDefault()
        return f.format(d)
    }

    fun getErrorMessage(it: Throwable) =
            if (it is RetrofitHttpError) {
                val httpErrorCode = it.errorResponse.code()
                val errorResponse = it.errorResponse.message()
                "[$httpErrorCode] $errorResponse"
            } else {
                "Couldn't refresh forecast"
            }
}
