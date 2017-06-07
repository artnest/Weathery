package artnest.weathery.view

import artnest.weathery.controller.fragments.MapFragment
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.verticalLayout

class MapFragmentUI : AnkoComponent<MapFragment> {

    override fun createView(ui: AnkoContext<MapFragment>) = with(ui) {
        verticalLayout {
        }
    }
}