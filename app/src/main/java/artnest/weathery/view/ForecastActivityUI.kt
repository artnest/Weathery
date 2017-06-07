package artnest.weathery.view

import android.support.v4.view.ViewPager
import artnest.weathery.R
import artnest.weathery.controller.activities.ForecastActivity
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.support.v4.viewPager

class ForecastActivityUI : AnkoComponent<ForecastActivity> {

    lateinit var pager: ViewPager

    override fun createView(ui: AnkoContext<ForecastActivity>) = with(ui) {
        frameLayout {
            pager = viewPager {
                id = R.id.view_pager
            }
        }
    }
}