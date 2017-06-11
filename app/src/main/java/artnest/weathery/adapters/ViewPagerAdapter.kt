package artnest.weathery.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import artnest.weathery.controller.fragments.ForecastParentFragment
import artnest.weathery.controller.fragments.MapFragment

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val PAGES_COUNT = 2

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return ForecastParentFragment()
            1 -> return MapFragment()
            else -> return ForecastParentFragment()
        }
    }

    override fun getCount() = PAGES_COUNT
}
