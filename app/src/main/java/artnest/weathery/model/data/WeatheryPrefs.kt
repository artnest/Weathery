package artnest.weathery.model.data

import com.chibatching.kotpref.KotprefModel

object WeatheryPrefs : KotprefModel() {
    var forecastType by intPref()
    var selectedCity by intPref(default = Cities.values().indexOf(Cities.Minsk))
}
