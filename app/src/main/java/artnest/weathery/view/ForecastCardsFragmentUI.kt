package artnest.weathery.view

import android.content.res.ColorStateList
import android.os.Build
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.widget.TextView
import artnest.weathery.R
import artnest.weathery.controller.fragments.ForecastCardsFragment
import artnest.weathery.model.data.Cities
import artnest.weathery.model.data.WeatheryPrefs
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.sdk25.coroutines.onClick

class ForecastCardsFragmentUI : AnkoComponent<ForecastCardsFragment> {

    lateinit var tb: Toolbar
    lateinit var fab: FloatingActionButton
    lateinit var tv: TextView

    override fun createView(ui: AnkoContext<ForecastCardsFragment>) = with(ui) {
        relativeLayout {
            tb = toolbar {
                id = R.id.toolbar
                backgroundColor = ContextCompat.getColor(ctx, R.color.colorAccent)
                setTitleTextColor(ContextCompat.getColor(ctx, android.R.color.white))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    elevation = 4 * ctx.resources.displayMetrics.density
                }
            }.lparams {
                width = matchParent

                val styledAttributes = ctx.theme.obtainStyledAttributes(
                        intArrayOf(android.R.attr.actionBarSize))
                height = styledAttributes.getDimension(
                        0, 56 * ctx.resources.displayMetrics.density).toInt()
                styledAttributes.recycle()
            }

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                view {
                    background = ContextCompat.getDrawable(ctx, R.drawable.shadow)
                }.lparams {
                    width = matchParent
                    height = dip(4)
                    bottomOf(tb)
                }
            }

            tv = textView {
                text = "Test Weather Card"
                gravity = Gravity.CENTER
            }.lparams {
                centerInParent()
            }

            fab = floatingActionButton {
                imageResource = R.drawable.ic_globe
                backgroundTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(ctx, R.color.colorPrimary))

                onClick {
                    val cities = mutableListOf<String>()
                    Cities.values().toList().forEach { it ->
                        cities.add(it.name)
                    }

                    ctx.selector("Forecast", cities) { d, i ->
                        WeatheryPrefs.selectedCity = i
                        toast("${cities[i]} has been selected")
                    }
                }
            }.lparams {
                margin = dip(20)
                alignParentBottom()
                alignParentRight()
            }
        }
    }
}