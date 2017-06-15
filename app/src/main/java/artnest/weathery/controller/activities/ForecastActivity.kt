package artnest.weathery.controller.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import artnest.weathery.R
import artnest.weathery.adapters.ViewPagerAdapter
import artnest.weathery.model.data.WeatheryPrefs
import artnest.weathery.view.ForecastActivityUI
import org.jetbrains.anko.setContentView

class ForecastActivity : AppCompatActivity() {

    private lateinit var forecastActivityUI: ForecastActivityUI
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        forecastActivityUI = ForecastActivityUI()
        forecastActivityUI.setContentView(this)

        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        forecastActivityUI.pager.adapter = viewPagerAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (WeatheryPrefs.forecastType == 0) {
            menu!!.findItem(R.id.action_view_list).isVisible = true
            menu.findItem(R.id.action_view_cards).isVisible = false
        } else {
            menu!!.findItem(R.id.action_view_list).isVisible = false
            menu.findItem(R.id.action_view_cards).isVisible = true
        }
        return true
    }
}
