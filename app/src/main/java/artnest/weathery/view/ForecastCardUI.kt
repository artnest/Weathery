package artnest.weathery.view

import android.graphics.Typeface
import android.view.ViewGroup
import android.widget.LinearLayout
import artnest.weathery.R
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class ForecastCardUI : AnkoComponent<ViewGroup> {

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        linearLayout {
            padding = dip(16)
            lparams(width = matchParent, height = wrapContent)

            cardView {
                radius = 8 * ctx.resources.displayMetrics.density

                relativeLayout {
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
                                textSize = 14f
                                setTypeface(typeface, Typeface.BOLD)
                            }

                            textView {
                                id = R.id.forecast_card_description
                                textSize = 14f
                                setTypeface(typeface, Typeface.ITALIC)
                            }.lparams {
                                leftMargin = dip(16)
                            }
                        }

                        textView {
                            id = R.id.forecast_card_temperature
                            textSize = 14f
                        }.lparams {
                            bottomOf(R.id.forecast_main_data)
                        }

                        linearLayout {
                            id = R.id.forecast_clouds_data
                            orientation = LinearLayout.HORIZONTAL

                            textView {
                                id = R.id.forecast_card_clouds
                                textSize = 14f
                            }

                            textView {
                                id = R.id.forecast_card_pressure
                                textSize = 14f
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
            }.lparams {
                width = matchParent
                height = wrapContent
            }
        }
    }
}