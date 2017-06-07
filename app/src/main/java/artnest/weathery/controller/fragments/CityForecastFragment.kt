package artnest.weathery.controller.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import artnest.weathery.view.CityForecastFragmentUI
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.ctx

class CityForecastFragment : Fragment() {

    lateinit var cityForecastFragmentUI: CityForecastFragmentUI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cityForecastFragmentUI = CityForecastFragmentUI()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val v = cityForecastFragmentUI.createView(AnkoContext.create(ctx, this))
        (activity as AppCompatActivity).setSupportActionBar(cityForecastFragmentUI.tb)
        return v
    }
}