package artnest.weathery.view

import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import artnest.weathery.R
import artnest.weathery.controller.fragments.CityForecastFragment
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.floatingActionButton

class CityForecastFragmentUI : AnkoComponent<CityForecastFragment> {

    lateinit var tb: Toolbar
    lateinit var fab: FloatingActionButton

    override fun createView(ui: AnkoContext<CityForecastFragment>) = with(ui) {
        relativeLayout {
            tb = toolbar {
                backgroundColor = ContextCompat.getColor(ctx, R.color.colorAccent)
                setTitleTextColor(ContextCompat.getColor(ctx, android.R.color.white))
            }.lparams {
                width = matchParent
                height = wrapContent
            }

            fab = floatingActionButton {
                imageResource = android.R.drawable.ic_input_add
            }.lparams {
                margin = dip(20)
                alignParentBottom()
                alignParentRight()
            }
        }
    }
}