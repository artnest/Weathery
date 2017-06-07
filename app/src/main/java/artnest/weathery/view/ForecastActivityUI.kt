package artnest.weathery.view

import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.support.v4.viewPager

class ForecastActivityUI : org.jetbrains.anko.AnkoComponent<ForecastActivity> {

    lateinit var pager: android.support.v4.view.ViewPager

    override fun createView(ui: org.jetbrains.anko.AnkoContext<ForecastActivity>) = with(ui) {
        frameLayout {
            pager = viewPager {
                id = artnest.weathery.R.id.view_pager
            }
        }
    }
}