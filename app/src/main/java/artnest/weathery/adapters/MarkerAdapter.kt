package artnest.weathery.adapters

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.view.View
import artnest.weathery.App
import artnest.weathery.R
import artnest.weathery.controller.activities.ForecastActivity
import artnest.weathery.controller.fragments.MapFragment
import artnest.weathery.helpers.Common
import artnest.weathery.helpers.loadUrl
import artnest.weathery.helpers.toWeatherInfo
import artnest.weathery.model.data.Cities
import artnest.weathery.model.data.CurrentWeatherInfo
import co.metalab.asyncawait.async
import co.metalab.asyncawait.awaitSuccessful
import com.google.android.gms.location.places.Places
import com.google.android.gms.maps.GoogleMap.*
import com.google.android.gms.maps.model.Marker
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast
import java.io.File
import java.io.IOException

class MarkerAdapter(val mapOwner: MapFragment) : OnMarkerClickListener,
        InfoWindowAdapter,
        OnInfoWindowClickListener,
        OnInfoWindowLongClickListener,
        OnInfoWindowCloseListener {

    private lateinit var item: CurrentWeatherInfo
    private var mBitmap: Bitmap? = null
    private var permissionsGranted = false
    private val displayMetrics = Common.getDisplayMetrics(mapOwner.ctx)

    override fun onMarkerClick(marker: Marker): Boolean {
        mapOwner.toast("Please wait, loading forecast...")

        async {
            val weather = awaitSuccessful(App.openWeather
                    .getCurrentForecast(Cities.valueOf(marker.title).id))
            mapOwner.mWeather = weather

            if (marker.snippet == null) {
                val places = awaitSuccessful(App.googlePlaces
                        .getNearbyPlaces(marker.position.latitude, marker.position.longitude))
                if (places.results.isNotEmpty()) {
                    await {
                        val result = Places.GeoDataApi
                                .getPlacePhotos((mapOwner.act as ForecastActivity)
                                        .googleApiClient,
                                        places.results[0].placeId)
                                .await()

                        if ((result != null) and (result.status.isSuccess)) {
                            val photoMetadataBuffer = result.photoMetadata
                            if (photoMetadataBuffer.count > 0) {
                                val photo = photoMetadataBuffer[0]
                                mBitmap = photo.getScaledPhoto(
                                        (mapOwner.act as ForecastActivity).googleApiClient,
                                        displayMetrics.widthPixels / 2,
                                        displayMetrics.heightPixels / 4
                                )
                                        .await()
                                        .bitmap
                            }
                            photoMetadataBuffer.release()
                        }
                    }
                }
            }

            marker.showInfoWindow()
        }.onError {
            mapOwner.toast(Common.getErrorMessage(it.cause))
        }
        return true
    }

    override fun getInfoWindow(marker: Marker) = null

    override fun getInfoContents(marker: Marker): View? {
        item = mapOwner.mWeather.toWeatherInfo()

        return with(mapOwner.ctx) {
            relativeLayout {
                padding = dip(8)

                textView {
                    id = R.id.info_city_name
                    text = item.name
                    textSize = 16f
                    setTypeface(typeface, Typeface.BOLD)
                }.lparams {
                    bottomOf(R.id.info)
                    centerHorizontally()
                }

                textView {
                    id = R.id.info_date
                    text = "Last updated: ${item.dt}"
                    textSize = 12f
                    setTypeface(typeface, Typeface.BOLD)
                }.lparams {
                    centerHorizontally()
                    bottomOf(R.id.info_city_name)
                    bottomMargin = dip(4)
                }

                imageView {
                    id = R.id.info_photo
                    val cityPhoto = Common.getCityPhotoFile(this@with, marker.title)
                    if (cityPhoto?.exists() ?: false) {
                        imageBitmap = BitmapFactory.decodeFile(cityPhoto!!.absolutePath)
                    } else {
                        if (mBitmap != null) {
                            imageBitmap = mBitmap
                        } else {
                            imageBitmap = BitmapFactory
                                    .decodeResource(resources, R.drawable.empty_photo)
                        }
                    }
                }.lparams {
                    centerHorizontally()
                    bottomOf(R.id.info_date)
                    bottomMargin = dip(4)
                }

                relativeLayout {
                    id = R.id.info_main

                    imageView {
                        id = R.id.info_icon
                        loadUrl(item.icon)
                    }.lparams {
                        width = dip(64)
                        height = dip(64)
                        centerVertically()
                        rightMargin = dip(8)
                    }

                    textView {
                        id = R.id.info_description
                        text = item.desc
                        textSize = 14f
                        setTypeface(typeface, Typeface.ITALIC)
                    }.lparams {
                        rightOf(R.id.info_icon)
                    }

                    textView {
                        id = R.id.info_temperature
                        text = item.temp
                        textSize = 14f
                    }.lparams {
                        bottomOf(R.id.info_description)
                        rightOf(R.id.info_icon)
                    }

                    textView {
                        id = R.id.info_sunrise
                        text = item.sunrise
                        textSize = 14f
                    }.lparams {
                        bottomOf(R.id.info_temperature)
                        rightOf(R.id.info_icon)
                        rightMargin = dip(16)
                    }

                    textView {
                        id = R.id.info_sunset
                        text = item.sunset
                        textSize = 14f
                    }.lparams {
                        bottomOf(R.id.info_sunrise)
                        rightOf(R.id.info_icon)
                    }
                }.lparams {
                    bottomOf(R.id.info_photo)
                }

                textView {
                    id = R.id.info_wind
                    text = item.wind
                    textSize = 14f
                }.lparams {
                    bottomOf(R.id.info_main)
                    rightMargin = dip(16)
                }

                textView {
                    id = R.id.info_clouds
                    text = item.clouds
                    textSize = 14f
                }.lparams {
                    bottomOf(R.id.info_main)
                    rightOf(R.id.info_wind)
                }

                textView {
                    id = R.id.info_pressure
                    text = item.pressure
                    textSize = 14f
                }.lparams {
                    bottomOf(R.id.info_wind)
                }

                textView {
                    id = R.id.info_humidity
                    text = item.humidity
                    textSize = 14f
                }.lparams {
                    bottomOf(R.id.info_pressure)
                }

                textView {
                    id = R.id.info_rain
                    text = item.rain
                    textSize = 14f
                }.lparams {
                    bottomOf(R.id.info_humidity)
                }

                textView {
                    id = R.id.info_snow
                    text = item.snow
                    textSize = 14f
                }.lparams {
                    bottomOf(R.id.info_rain)
                }
            }
        }
    }

    override fun onInfoWindowClick(marker: Marker) {
        val permissionsListener = object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    if (report.grantedPermissionResponses.find { response ->
                        response.permissionName == Manifest.permission.CAMERA
                    } != null) {
                        permissionsGranted = true
                    }
                } else {
                    if (report.areAllPermissionsGranted()) {
                        permissionsGranted = true
                    }
                }

                if (permissionsGranted) {
                    with(mapOwner.ctx) {
                        alert {
                            title = item.name
                            message = getString(R.string.take_photo_message)

                            positiveButton(R.string.take_photo_positive_button) {
                                val cityPhoto = Common.getCityPhotoFile(this@with, marker.title)
                                if (cityPhoto?.exists() ?: false) {
                                    cityPhoto!!.delete()
                                }
                                marker.hideInfoWindow()
                                dispatchTakePictureIntent(marker.title)
                            }

                            cancelButton {
                            }
                        }.show()
                    }
                }
            }

            override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>,
                                                            token: PermissionToken) {
                with(mapOwner.ctx) {
                    alert {
                        title = getString(R.string.camera_storage_permissions)
                        message = getString(R.string.camera_storage_permissions_message_rationale)

                        okButton {
                        }
                    }.show()
                    token.cancelPermissionRequest()
                }
            }
        }

        Dexter.withActivity(mapOwner.act)
                .withPermissions(
                        Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(permissionsListener)
                .check()
    }

    override fun onInfoWindowLongClick(marker: Marker) {
        if (permissionsGranted) {
            with(mapOwner.ctx) {
                val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                val cityPhoto = File(storageDir, "${marker.title}.jpg")
                if (cityPhoto.exists()) {
                    alert {
                        title = item.name
                        message = getString(R.string.remove_photo_message)

                        positiveButton(getString(R.string.remove_photo_positive_button)) {
                            cityPhoto.delete()
                            marker.hideInfoWindow()
                            toast("Your photo was removed")
                        }

                        cancelButton {
                        }
                    }.show()
                } else {
                    toast("No user's photo to remove")
                }
            }
        } else {
            mapOwner.toast("No user's photo to remove")
        }
    }

    override fun onInfoWindowClose(marker: Marker) {
        mBitmap = null
    }

    fun dispatchTakePictureIntent(cityName: String) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(mapOwner.ctx.packageManager) != null) {
            var photoFile: File? = null
            try {
                photoFile = Common.createImageFile(mapOwner.ctx, cityName)
            } catch (ex: IOException) {
                mapOwner.toast("Could not save photo!")
            }

            if (photoFile != null) {
                val authorities = mapOwner.ctx.packageName + ".fileprovider"
                val photoUri = FileProvider.getUriForFile(mapOwner.ctx, authorities, photoFile)
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                mapOwner.startActivityForResult(takePictureIntent, mapOwner.REQUEST_IMAGE_CAPTURE)
            }
        }
    }
}
