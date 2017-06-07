package artnest.weathery.controller.fragments

import org.jetbrains.anko.support.v4.ctx

class CityForecastFragment : android.support.v4.app.Fragment() {

    lateinit var cityForecastFragmentUI: artnest.weathery.view.CityForecastFragmentUI

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)

        cityForecastFragmentUI = artnest.weathery.view.CityForecastFragmentUI()
    }

    override fun onCreateView(inflater: android.view.LayoutInflater?, container: android.view.ViewGroup?, savedInstanceState: android.os.Bundle?): android.view.View {
        val v = cityForecastFragmentUI.createView(org.jetbrains.anko.AnkoContext.Companion.create(ctx, this))
        (activity as android.support.v7.app.AppCompatActivity).setSupportActionBar(cityForecastFragmentUI.tb)
        return v
    }
}