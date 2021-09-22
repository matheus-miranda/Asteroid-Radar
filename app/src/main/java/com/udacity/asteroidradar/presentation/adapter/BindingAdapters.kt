package com.udacity.asteroidradar.presentation.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
        imageView.contentDescription =
            imageView.context.getString(R.string.potentially_hazardous_asteroid_image)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
        imageView.contentDescription =
            imageView.context.getString(R.string.not_hazardous_asteroid_image)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("astronomyPicture")
fun bindAstronomyPicture(imageView: ImageView, imageUrl: String?) {
    imageUrl?.let {
        Picasso.with(imageView.context).load(imageUrl).fit().centerCrop()
            .placeholder(R.drawable.placeholder_picture_of_day)
            .error(R.drawable.ic_broken_image)
            .into(imageView)
    }
}

@BindingAdapter("astronomyPictureDescription")
fun bindAstronomyPictureContent(imageView: ImageView, description: String?) {
    description?.let {
        imageView.contentDescription = imageView.context.getString(
            R.string.nasa_picture_of_day_content_description_format,
            description
        )
    }
}

@BindingAdapter("apiText")
fun bindSimpleText(textView: TextView, title: String?) {
    title?.let {
        textView.text = title
    }
}