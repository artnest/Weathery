package artnest.weathery.helpers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.DisplayMetrics
import co.metalab.asyncawait.RetrofitHttpError
import khronos.toDate
import khronos.toString
import org.jetbrains.anko.windowManager
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object Common {
    fun getImage(icon: String) = "http://openweathermap.org/img/w/$icon.png"
    fun getDate(rawDate: String) = rawDate.toDate("yyyy-MM-dd HH:mm:ss").toString("EEE d MMM")
    fun getHours(rawDate: String) = rawDate.toDate("yyyy-MM-dd HH:mm:ss").toString("HH:mm")
    fun getTemperature(temp: Double) = "$temp °C"
    fun getClouds(clouds: Int) = "clouds: $clouds %"
    fun getPressure(pressure: Double) = "$pressure hpa"
    fun getWind(speed: Double) = "$speed m/s"
    fun getHumidity(humidity: Int) = "humidity: $humidity %"
    fun getRain(rain: Double?) = "rain: ${rain?.format(4) ?: "no rain"}"
    fun getSnow(snow: Double?) = "snow: ${snow?.format(4) ?: "no snow"}"
    fun getSunrise(timestamp: Long) = "sunrise: ${unixTimestampToDateTime(timestamp)}"
    fun getSunset(timestamp: Long) = "sunset: ${unixTimestampToDateTime(timestamp)}"

    fun unixTimestampToDateTime(timestamp: Long): String {
        val d = Date()
        d.time = timestamp * 1000
        val f = SimpleDateFormat("HH:mm", Locale.US)
        f.timeZone = TimeZone.getDefault()
        return f.format(d)
    }

    fun getPlaceLocation(lan: Double, lon: Double) = "$lan,$lon"

    fun getErrorMessage(it: Throwable?) =
            if (it is RetrofitHttpError) {
                val httpErrorCode = it.errorResponse.code()
                val errorResponse = it.errorResponse.message()
                "[$httpErrorCode] $errorResponse"
            } else {
                "Could not refresh forecast"
            }

    fun getPicturesStorageDir(ctx: Context): File? =
            ctx.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

    fun getCityPhotoFile(ctx: Context, cityName: String): File? {
        val picturesStorageDir = getPicturesStorageDir(ctx)
        if (picturesStorageDir != null) {
            return File(picturesStorageDir, "$cityName.jpg")
        }
        return null
    }

    @Throws(IOException::class)
    fun createImageFile(ctx: Context, cityName: String): File {
        val imageFileName = cityName + ".jpg"
        val storageDir = getPicturesStorageDir(ctx)
        if (storageDir?.exists()?.not() ?: true) {
            storageDir!!.mkdirs()
        }
        // val imageFile = createTempFile(imageFileName, ".jpg", storageDir!!)
        val imageFile = File(storageDir!!, imageFileName)
        return imageFile
    }

    fun getLastModifiedFile(directory: File): File? {
        val files = directory.listFiles()
        if ((files == null) or (files.isEmpty())) {
            return null
        }

        var lastModifiedFile = files[0]
        (1..files.size - 1)
                .asSequence()
                .filter { lastModifiedFile.lastModified() < files[it].lastModified() }
                .forEach { lastModifiedFile = files[it] }
        return lastModifiedFile
    }

    fun saveScaledBitmap(photoPath: String, width: Int = 400, height: Int = 400) {
        val bmOptions = BitmapFactory.Options()
        bmOptions.inJustDecodeBounds = true
        BitmapFactory.decodeFile(photoPath, bmOptions)
        val bitmapWidth = bmOptions.outWidth
        val bitmapHeight = bmOptions.outHeight

        val scaleFactor = minOf(bitmapWidth / width, bitmapHeight / height)

        bmOptions.inJustDecodeBounds = false
        bmOptions.inSampleSize = scaleFactor

        val bitmap = BitmapFactory.decodeFile(photoPath, bmOptions)
        val fileOutputStream = FileOutputStream(photoPath)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
    }

    fun getDisplayMetrics(ctx: Context): DisplayMetrics {
        val displayMetrics = DisplayMetrics()
        ctx.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics
    }
}
