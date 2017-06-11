package artnest.weathery.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import artnest.weathery.R
import artnest.weathery.controller.fragments.ForecastCardsFragment
import artnest.weathery.controller.fragments.ForecastParentFragment
import artnest.weathery.controller.fragments.MapFragment

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val PAGES_COUNT = 2

    lateinit var forecastParentFragment: ForecastParentFragment

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                forecastParentFragment = ForecastParentFragment()
                return forecastParentFragment
            }
            1 -> {
                return MapFragment()
            }
            else -> {
                forecastParentFragment = ForecastParentFragment()
                return forecastParentFragment
            }
        }
    }

    override fun getCount() = PAGES_COUNT

    fun changeForecastView() {
        forecastParentFragment.childFragmentManager.beginTransaction()
                .replace(R.id.child_fragment_container, ForecastCardsFragment())
                .commit()
    }
}
