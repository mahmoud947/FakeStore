package com.example.fakestore.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.data.models.Product
import com.example.fakestore.databinding.ItemProductBinding

class TestAdapter(private val interaction: Interaction? = null) :
    ListAdapter<Product, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>() {

            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                TODO("not implemented")
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                TODO("not implemented")
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return TestViewHolder.from(parent, interaction = interaction)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TestViewHolder -> {
                val item = getItem(position)
                holder.onBind(item)
            }
        }
    }


    class TestViewHolder
    constructor(
        private val binding: ItemProductBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: Product) {
            binding.product = item
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                interaction?.onItemSelected(bindingAdapterPosition, item)
            }
        }


        companion object {
            fun from(viewGroup: ViewGroup, interaction: Interaction?): TestViewHolder {
                val bind = ItemProductBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
                return TestViewHolder(bind, interaction = interaction)
            }
        }


    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Product)
    }
}