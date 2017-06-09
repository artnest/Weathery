package artnest.weathery.view

import android.content.res.ColorStateList
import android.os.Build
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.widget.TextView
import artnest.weathery.R
import artnest.weathery.controller.fragments.CityForecastFragment
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.floatingActionButton

class CityForecastFragmentUI : AnkoComponent<CityForecastFragment> {

    lateinit var tb: Toolbar
    lateinit var fab: FloatingActionButton
    lateinit var tv: TextView

    override fun createView(ui: AnkoContext<CityForecastFragment>) = with(ui) {
        relativeLayout {
            tb = toolbar {
                backgroundColor = ContextCompat.getColor(ctx, R.color.colorAccent)
                setTitleTextColor(ContextCompat.getColor(ctx, android.R.color.white))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    elevation = 4f
                }
            }.lparams {
                width = matchParent
                height = wrapContent
            }

            tv = textView {
                text = "Test Weather"
            }.lparams {
                centerInParent()
            }

            fab = floatingActionButton {
                imageResource = R.drawable.ic_globe
                backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(ctx, R.color.colorPrimary))
            }.lparams {
                margin = dip(20)
                alignParentBottom()
                alignParentRight()
            }
        }
    }
}