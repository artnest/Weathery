package artnest.weathery.helpers

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ViewGroup.inflate(layoutRes: Int) = LayoutInflater.from(context).inflate(layoutRes, this, false)
fun ImageView.loadUrl(url: String) = Picasso.with(context).load(url).into(this)
fun Double.format(digits: Int) = String.format("%.${digits}f", this)
