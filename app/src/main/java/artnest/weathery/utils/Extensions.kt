package artnest.weathery.utils

import android.os.Build
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.ViewManager
import artnest.weathery.R
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar

fun ViewManager.toolbarShadow() =
        linearLayout {
            id = R.id.toolbar_layout
            toolbar {
                id = R.id.toolbar
                backgroundColor = ContextCompat.getColor(context, R.color.colorAccent)
                setTitleTextColor(ContextCompat.getColor(context, android.R.color.white))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    elevation = 4 * context.resources.displayMetrics.density
                }
            }.lparams {
                width = matchParent

                val typedValue = TypedValue()
                context.theme.resolveAttribute(android.R.attr.actionBarSize, typedValue, true)
                val toolbarSizeAttr = intArrayOf(android.R.attr.actionBarSize)
                val indexOfAttrToolbarSize = 0
                val array = context.obtainStyledAttributes(typedValue.data, toolbarSizeAttr)

                height = array.getInt(indexOfAttrToolbarSize, dip(56))
                array.recycle()
            }

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                view {
                    background = ContextCompat.getDrawable(context, R.drawable.shadow)
                }.lparams {
                    width = matchParent
                    height = dip(4)
                }
            }
        }
