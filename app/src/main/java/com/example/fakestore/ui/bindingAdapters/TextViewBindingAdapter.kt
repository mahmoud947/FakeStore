package com.example.fakestore.ui.bindingAdapters

import android.graphics.Paint
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("oldPrice")
fun setOldPrice(textView: TextView,value:String){
    textView.apply {
        text = value
        paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }
}