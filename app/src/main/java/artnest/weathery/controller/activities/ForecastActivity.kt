package artnest.weathery.controller.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import artnest.weathery.adapters.ViewPagerAdapter
import artnest.weathery.view.ForecastActivityUI
import org.jetbrains.anko.setContentView

class ForecastActivity : AppCompatActivity() {

    lateinit var forecastActivityUI: ForecastActivityUI
    lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        forecastActivityUI = ForecastActivityUI()
        forecastActivityUI.setContentView(this)

        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        forecastActivityUI.pager.adapter = viewPagerAdapter
    }
}
