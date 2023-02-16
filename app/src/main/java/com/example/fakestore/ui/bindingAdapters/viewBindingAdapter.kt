package com.example.fakestore.ui.bindingAdapters

import android.view.View
import androidx.databinding.BindingAdapter


@BindingAdapter("goneIfTrue")
fun goneIfTrue(view: View, boolean: Boolean) {
    view.visibility = if (boolean) View.GONE else View.VISIBLE
}

