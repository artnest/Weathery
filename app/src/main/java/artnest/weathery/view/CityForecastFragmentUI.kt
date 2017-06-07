package artnest.weathery.view

import artnest.weathery.controller.fragments.CityForecastFragment
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.floatingActionButton

class CityForecastFragmentUI : org.jetbrains.anko.AnkoComponent<CityForecastFragment> {

    lateinit var tb: android.support.v7.widget.Toolbar
    lateinit var fab: android.support.design.widget.FloatingActionButton

    override fun createView(ui: org.jetbrains.anko.AnkoContext<CityForecastFragment>) = with(ui) {
        relativeLayout {
            tb = toolbar {
                backgroundColor = android.support.v4.content.ContextCompat.getColor(ctx, artnest.weathery.R.color.colorAccent)
                setTitleTextColor(android.support.v4.content.ContextCompat.getColor(ctx, android.R.color.white))
            }.lparams {
                width = org.jetbrains.anko.matchParent
                height = org.jetbrains.anko.wrapContent
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