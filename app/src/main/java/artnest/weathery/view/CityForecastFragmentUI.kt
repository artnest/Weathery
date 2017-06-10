package artnest.weathery.view

import android.content.res.ColorStateList
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.widget.TextView
import artnest.weathery.R
import artnest.weathery.controller.fragments.CityForecastFragment
import artnest.weathery.utils.toolbarShadow
import org.jetbrains.anko.*
import org.jetbrains.anko.design.floatingActionButton

class CityForecastFragmentUI : AnkoComponent<CityForecastFragment> {

    lateinit var tb: Toolbar
    lateinit var fab: FloatingActionButton
    lateinit var tv: TextView

    override fun createView(ui: AnkoContext<CityForecastFragment>) = with(ui) {
        ForecastRootLayout<CityForecastFragment>({
            tv = textView {
                text = "Test Weather"
                gravity = Gravity.CENTER
            }.lparams {
                centerInParent()
            }
        }).createView(ui)
    }
}