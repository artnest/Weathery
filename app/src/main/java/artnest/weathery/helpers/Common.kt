package artnest.weathery.helpers

import khronos.toDate
import khronos.toString

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
}
