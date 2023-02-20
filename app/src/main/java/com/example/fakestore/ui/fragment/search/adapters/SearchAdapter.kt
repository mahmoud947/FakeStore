package com.example.fakestore.ui.fragment.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.data.models.response.Product
import com.example.fakestore.databinding.ItemSearchResultBinding

class SearchAdapter(private val interaction: Interaction? = null) :
    ListAdapter<Product, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>() {

            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem == newItem

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SearchViewHolder.from(parent, interaction = interaction)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SearchViewHolder -> {
                val item = getItem(position)
                holder.onBind(item)
            }
        }
    }


    class SearchViewHolder
    constructor(
        private val binding: ItemSearchResultBinding,
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
            fun from(viewGroup: ViewGroup, interaction: Interaction?): SearchViewHolder {
                val bind = ItemSearchResultBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
                return SearchViewHolder(bind, interaction = interaction)
            }
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Product)
    }
}