package artnest.weathery.helpers

import khronos.toDate
import khronos.toString

object Common {
    fun getImage(icon: String) = "http://openweathermap.org/img/w/$icon.png"
    fun getDate(rawDate: String) = rawDate.toDate("yyyy-MM-dd HH:mm:ss").toString("EEE d MMM")
    fun getTemperature(temp: Double) = "$temp °C"
    fun getClouds(clouds: Int) = "clouds: $clouds %"
    fun getPressure(pressure: Double) = "$pressure hpa"
}
