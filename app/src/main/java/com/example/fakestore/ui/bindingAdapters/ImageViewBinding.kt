package com.example.fakestore.ui.bindingAdapters

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat


@BindingAdapter("setImageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    Glide.with(imageView.context)
        .load(url)
        .format(DecodeFormat.PREFER_RGB_565)
        .into(imageView)
}


@BindingAdapter("setImageSrc")
fun setImageUrl(
    imageView: ImageView,
    @DrawableRes
    src: Int
) {
    imageView.setImageResource(src)
}