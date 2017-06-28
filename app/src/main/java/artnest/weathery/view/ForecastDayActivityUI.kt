package artnest.weathery.view

import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.widget.ListView
import artnest.weathery.R
import artnest.weathery.adapters.ForecastDayListViewAdapter
import artnest.weathery.controller.activities.ForecastDayActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar

class ForecastDayActivityUI : AnkoComponent<ForecastDayActivity> {

    lateinit var tb: Toolbar
    lateinit var lv: ListView

    override fun createView(ui: AnkoContext<ForecastDayActivity>) = with(ui) {
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

            lv = listView {
                id = android.R.id.list
                adapter = ForecastDayListViewAdapter(owner)
            }.lparams {
                width = matchParent
                height = matchParent
                bottomOf(tb)
            }
        }
    }
}
