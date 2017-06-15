package artnest.weathery.controller.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import artnest.weathery.R
import artnest.weathery.controller.activities.ForecastActivity.Companion.showAction
import artnest.weathery.model.data.WeatheryPrefs
import artnest.weathery.model.gson.ExtendedWeather
import artnest.weathery.view.ForecastParentFragmentUI
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.ctx

class ForecastParentFragment : Fragment() {

    companion object {
        var mWeather: ExtendedWeather? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) =
            ForecastParentFragmentUI().createView(AnkoContext.create(ctx, this))

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        childFragmentManager.beginTransaction()
                .replace(R.id.child_fragment_container,
                        if (WeatheryPrefs.forecastType == 0) {
                            ForecastListFragment()
                        }
                        else {
                            ForecastCardsFragment.newInstance(mWeather)
                        })
                .commit()

        // TODO retain state instance on orientation change (use SharedPreferences)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_refresh -> {
                return true
            }

            R.id.action_view_list -> {
                showAction = false
                WeatheryPrefs.forecastType = 1
                activity.supportInvalidateOptionsMenu()

                childFragmentManager.beginTransaction()
                        .replace(R.id.child_fragment_container, ForecastCardsFragment())
                        .commit()

                return true
            }

            R.id.action_view_cards -> {
                showAction = true
                WeatheryPrefs.forecastType = 0
                activity.supportInvalidateOptionsMenu()

                childFragmentManager.beginTransaction()
                        .replace(R.id.child_fragment_container, ForecastListFragment())
                        .commit()

                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }
}