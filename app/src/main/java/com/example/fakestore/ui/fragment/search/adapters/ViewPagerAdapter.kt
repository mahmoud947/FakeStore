package com.example.fakestore.ui.fragment.search.adapters

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.fakestore.ui.fragment.shop.category.CategoryFragment

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
) : FragmentStateAdapter(
    fragmentManager,
    lifecycle
) {
    private val _data: MutableList<String> = mutableListOf()

    override fun getItemCount(): Int = _data.size

    override fun createFragment(position: Int): Fragment=
        CategoryFragment.newInstance(_data[position])

    @SuppressLint("NotifyDataSetChanged")
    fun submitData(data:List<String>){
        _data.addAll(data)
        notifyDataSetChanged()
    }
}