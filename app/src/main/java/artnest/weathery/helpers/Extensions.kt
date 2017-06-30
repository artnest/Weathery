package artnest.weathery.helpers

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ViewGroup.inflate(layoutRes: Int) = LayoutInflater.from(context).inflate(layoutRes, this, false)
fun ImageView.loadUrl(ic: String) = Picasso.with(context).load(Common.getImage(ic)).into(this)
