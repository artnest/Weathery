package artnest.weathery.model.data

data class WeatherInfoDetails(
        val dt: String,
        val icon: String,
        val desc: String,
        val temp: String,
        val wind: String,
        val clouds: String,
        val pressure: String,
        val humidity: String,
        val rain: String,
        val snow: String
)
