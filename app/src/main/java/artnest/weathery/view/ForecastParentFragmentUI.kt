package artnest.weathery.view

import artnest.weathery.R
import artnest.weathery.controller.fragments.ForecastParentFragment
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.frameLayout

class ForecastParentFragmentUI : AnkoComponent<ForecastParentFragment> {

    override fun createView(ui: AnkoContext<ForecastParentFragment>) = with(ui) {
        frameLayout {
            id = R.id.child_fragment_container
        }
    }
}