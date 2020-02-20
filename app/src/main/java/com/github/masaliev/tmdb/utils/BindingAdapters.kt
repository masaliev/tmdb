package com.github.masaliev.tmdb.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.github.masaliev.tmdb.R
import com.squareup.picasso.Picasso

class BindingAdapters {
    companion object {
        @JvmStatic
        @BindingAdapter("image")
        fun loadImage(imageView: ImageView, imageUrl: String?) {
            //@TODO use glide to more customize
            Picasso.get()
                .load(imageUrl?.let {
                    "https://image.tmdb.org/t/p/w300$it" //@TODO get from /configuration endpoint
                })
                .placeholder(R.drawable.film_poster_placeholder)
                .into(imageView)
        }
    }
}