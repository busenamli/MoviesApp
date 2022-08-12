package com.busenamli.moviesapp.util

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.busenamli.moviesapp.R
import com.busenamli.moviesapp.util.Constants.IMAGE_URL
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun ImageView.downloadFromUrl(url: String?, context: Context){

    val options = RequestOptions()
        .error(R.drawable.circle_background)
        .placeholder(CircularProgressDrawable(context).apply {
            strokeWidth = 8f
            centerRadius = 40f
            start()
        })

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(IMAGE_URL+url)
        .fitCenter()
        .into(this)
}

fun View.changeVisibility(visible: Boolean){
    visibility = if (visible) View.VISIBLE else View.GONE
}

fun doubleFormat(double: Double): Double{
    val df = DecimalFormat("#.#")
    return df.format(double).toDouble()
}

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("android:dateFormat")
fun dateFormat(view: TextView, date: String?){
    date?.let {
        val formatter = DateTimeFormatter.ofPattern("yyyy")
        val dateFormatted = LocalDate.parse(date).format(formatter)
        println(dateFormatted)
        view.text = dateFormatted.toString()
    }
}

@BindingAdapter("android:runtimeFormat")
fun runtimeFormat(view: TextView, runtime: Int?){
    runtime?.let {
        val runtimeText = "${runtime/60}sa. ${runtime%60}dk."
        view.text = runtimeText
    }
}

@BindingAdapter("android:doubleFormat")
fun doubleFormat(view: TextView, double:Double?){
    double?.let {
        view.text = doubleFormat(it).toString() }
}

@BindingAdapter("android:downloadUrl")
fun downloadImage(view: ImageView, url:String?){
    view.downloadFromUrl(url,view.context)
}