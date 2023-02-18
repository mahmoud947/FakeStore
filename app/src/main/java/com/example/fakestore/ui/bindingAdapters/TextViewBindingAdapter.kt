package com.example.fakestore.ui.bindingAdapters

import android.graphics.Paint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.fakestore.R
import java.text.DecimalFormat

@BindingAdapter("oldPrice")
fun setOldPrice(textView: TextView,value:String){
    textView.apply {
        text = value
        paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }
}

@BindingAdapter("ratingCount")
fun setRatingCount(textView: TextView,value:Double){
    val df = DecimalFormat("#.#")
    textView.apply {
        text = textView.context.getString(R.string.rate_count,df.format(value))
    }
}