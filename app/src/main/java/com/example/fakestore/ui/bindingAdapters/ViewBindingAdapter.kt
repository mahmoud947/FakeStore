package com.example.fakestore.ui.bindingAdapters

import android.view.View
import android.view.animation.Animation
import androidx.databinding.BindingAdapter


@BindingAdapter("goneIfTrue")
fun goneIfTrue(view: View, boolean: Boolean) {
    view.visibility = if (boolean) View.GONE else View.VISIBLE
}

@BindingAdapter("setAnimation")
fun setAnimation(view: View, anim: Animation) {
    view.apply {
        animation = anim
    }
}


