package artnest.weathery.view

import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import artnest.weathery.R
import artnest.weathery.adapters.ForecastDayCardsAdapter
import artnest.weathery.controller.activities.ForecastDayCardsActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.recyclerview.v7.recyclerView

class ForecastDayCardsActivityUI : AnkoComponent<ForecastDayCardsActivity> {

    lateinit var tb: Toolbar
    lateinit var rv: RecyclerView

    override fun createView(ui: AnkoContext<ForecastDayCardsActivity>) = with(ui) {
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

            rv = recyclerView {
                id = R.id.recycler_view
                if (configuration.portrait) {
                    layoutManager = GridLayoutManager(ctx, 2)
                } else {
                    layoutManager = GridLayoutManager(ctx, 3)
                }
                setHasFixedSize(true)
                adapter = ForecastDayCardsAdapter(owner)
            }.lparams {
                width = matchParent
                height = matchParent
                bottomOf(tb)
            }
        }
    }
}
