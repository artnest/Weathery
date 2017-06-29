package artnest.weathery.view

import android.graphics.Typeface
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import artnest.weathery.R
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class ForecastCardUI : AnkoComponent<ViewGroup> {

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        cardView {
            radius = 4 * ctx.resources.displayMetrics.density

            relativeLayout {
                // lparams(width = matchParent, height = matchParent)
                padding = dip(8)

                imageView {
                    id = R.id.forecast_card_icon
                }.lparams {
                    width = dip(64)
                    height = dip(64)
                    centerVertically()
                }

                relativeLayout {
                    linearLayout {
                        id = R.id.forecast_main_data
                        orientation = LinearLayout.HORIZONTAL

                        textView {
                            id = R.id.forecast_card_date
                            setTypeface(typeface, Typeface.BOLD)
                        }

                        textView {
                            id = R.id.forecast_card_description
                            setTypeface(typeface, Typeface.ITALIC)
                        }.lparams {
                            leftMargin = dip(16)
                        }
                    }

                    textView {
                        id = R.id.forecast_card_temperature
                    }.lparams {
                        bottomOf(R.id.forecast_main_data)
                    }

                    linearLayout {
                        id = R.id.forecast_clouds_data
                        orientation = LinearLayout.HORIZONTAL

                        textView {
                            id = R.id.forecast_card_clouds
                        }

                        textView {
                            id = R.id.forecast_card_pressure
                        }.lparams {
                            leftMargin = dip(16)
                        }
                    }.lparams {
                        bottomOf(R.id.forecast_card_temperature)
                    }
                }.lparams {
                    leftMargin = dip(4)
                    rightOf(R.id.forecast_card_icon)
                }
            }
        }.applyRecursively { view ->
            when (view) {
                is TextView -> view.textSize = 14f
            }
        }
    }
}