package artnest.weathery.view

import android.content.res.ColorStateList
import android.os.Build
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.widget.ListView
import artnest.weathery.R
import artnest.weathery.adapters.ForecastListViewAdapter
import artnest.weathery.controller.fragments.ForecastListFragment
import artnest.weathery.model.data.Cities
import artnest.weathery.model.data.WeatheryPrefs
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.sdk25.coroutines.onClick

class ForecastListFragmentUI : AnkoComponent<ForecastListFragment> {

    lateinit var tb: Toolbar
    lateinit var fab: FloatingActionButton
    lateinit var lv: ListView

    override fun createView(ui: AnkoContext<ForecastListFragment>) = with(ui) {
        relativeLayout {
            tb = toolbar {
                id = R.id.toolbar
                backgroundColor = ContextCompat.getColor(ctx, R.color.colorAccent)
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

            lv = listView {
                id = android.R.id.list
                adapter = ForecastListViewAdapter(owner)
            }.lparams {
                width = matchParent
                height = matchParent
                bottomOf(tb)
            }

            textView {
                id = android.R.id.empty
                text = ctx.getString(R.string.empty)
                textSize = 24f
                padding = dip(24)
                gravity = Gravity.CENTER
            }.lparams {
                width = matchParent
                height = matchParent
            }

            fab = floatingActionButton {
                imageResource = R.drawable.ic_globe
                backgroundTintList = ColorStateList
                        .valueOf(ContextCompat.getColor(ctx, R.color.colorPrimary))

                /*val icon = AppCompatResources.getDrawable(ctx, R.drawable.ic_globe)!!
                val newIcon = icon.constantState.newDrawable()
                newIcon.mutate().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
                setImageDrawable(newIcon)*/

                onClick {
                    val cities = mutableListOf<String>()
                    Cities.values().toList().forEach { c ->
                        cities.add(c.name)
                    }

                    ctx.selector("Forecast", cities) { _, i ->
                        WeatheryPrefs.selectedCity = i
                        toast("${cities[i]} has been selected")
                        (lv.adapter as ForecastListViewAdapter).reload()
                    }
                }
            }.lparams {
                margin = dip(16)
                alignParentBottom()
                alignParentRight()
            }
        }
    }
}
