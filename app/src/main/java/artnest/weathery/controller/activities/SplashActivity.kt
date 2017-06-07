package artnest.weathery.controller.activities

import org.jetbrains.anko.startActivity

class SplashActivity : android.support.v7.app.AppCompatActivity() {

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity<artnest.weathery.ForecastActivity>()
        finish()
    }
}
