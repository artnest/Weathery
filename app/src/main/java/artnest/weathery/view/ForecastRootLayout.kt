package artnest.weathery.view

import android.content.Context
import android.content.res.ColorStateList
import android.support.v4.content.ContextCompat
import artnest.weathery.R
import artnest.weathery.utils.toolbarShadow
import org.jetbrains.anko.*
import org.jetbrains.anko.design.floatingActionButton

class ForecastRootLayout<T : Context>(val customize: AnkoContext<T>.() -> Unit = {}) : AnkoComponent<T> {

    override fun createView(ui: AnkoContext<T>) = with(ui) {
        relativeLayout {
            toolbarShadow().lparams {
                width = matchParent
                height = wrapContent
            }

            customize()

            floatingActionButton {
                id = R.id.floating_action_button
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