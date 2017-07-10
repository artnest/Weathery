package artnest.weathery.controller.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import artnest.weathery.R
import artnest.weathery.model.data.WeatheryPrefs
import artnest.weathery.view.ForecastActivityUI
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.Places
import org.jetbrains.anko.setContentView

class ForecastActivity : AppCompatActivity(), OnConnectionFailedListener {

    private lateinit var forecastActivityUI: ForecastActivityUI
    lateinit var googleApiClient: GoogleApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        forecastActivityUI = ForecastActivityUI()
        forecastActivityUI.setContentView(this)

        googleApiClient = GoogleApiClient
                .Builder(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (WeatheryPrefs.forecastType == 0) {
            menu!!.findItem(R.id.action_view_list).isVisible = false
            menu.findItem(R.id.action_view_cards).isVisible = true
        } else {
            menu!!.findItem(R.id.action_view_list).isVisible = true
            menu.findItem(R.id.action_view_cards).isVisible = false
        }
        return true
    }

    override fun onConnectionFailed(result: ConnectionResult) {
        // toast("No Internet Connection")
    }
}
