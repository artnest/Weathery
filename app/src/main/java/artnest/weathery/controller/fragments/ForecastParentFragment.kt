package artnest.weathery.controller.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import artnest.weathery.R
import artnest.weathery.view.ForecastParentFragmentUI
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.ctx

class ForecastParentFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) =
            ForecastParentFragmentUI().createView(AnkoContext.create(ctx, this))

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        childFragmentManager.beginTransaction()
                .replace(R.id.child_fragment_container, ForecastListFragment())
                .commit()

        // TODO retain state instance on orientation change (use SharedPreferences)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        activity.menuInflater.inflate(R.menu.menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)

        if (showAction) {
            menu!!.findItem(R.id.action_view_list).isVisible = true
            menu.findItem(R.id.action_view_cards).isVisible = false
        } else {
            menu!!.findItem(R.id.action_view_list).isVisible = false
            menu.findItem(R.id.action_view_cards).isVisible = true
        }
    }

    var showAction = true

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_refresh -> {
                return true
            }

            R.id.action_view_list -> {
                showAction = false
                activity.supportInvalidateOptionsMenu()

                childFragmentManager.beginTransaction()
                        .replace(R.id.child_fragment_container, ForecastCardsFragment())
                        .commit()

                return true
            }

            R.id.action_view_cards -> {
                showAction = true
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