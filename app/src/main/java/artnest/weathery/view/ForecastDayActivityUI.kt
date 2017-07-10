package artnest.weathery.view

import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.widget.GridView
import artnest.weathery.R
import artnest.weathery.adapters.ForecastDayAdapter
import artnest.weathery.controller.activities.ForecastDayActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar

class ForecastDayActivityUI : AnkoComponent<ForecastDayActivity> {

    lateinit var tb: Toolbar
    lateinit var gv: GridView

    override fun createView(ui: AnkoContext<ForecastDayActivity>) = with(ui) {
        relativeLayout {
            tb = toolbar {
                id = R.id.toolbar
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    elevation = 4 * ctx.resources.displayMetrics.density
                }
            }.lparams {
                width = matchParent

                val styledAttributes = ctx.theme
                        .obtainStyledAttributes(intArrayOf(android.R.attr.actionBarSize))
                height = styledAttributes
                        .getDimension(0, 56 * ctx.resources.displayMetrics.density).toInt()
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

            gv = gridView {
                adapter = ForecastDayAdapter(owner)
                if (configuration.portrait) {
                    numColumns = 2
                } else {
                    numColumns = 3
                }
                verticalSpacing = 8
                horizontalSpacing = 8
            }.lparams {
                width = matchParent
                height = matchParent
                bottomOf(tb)
            }
        }
    }
}
