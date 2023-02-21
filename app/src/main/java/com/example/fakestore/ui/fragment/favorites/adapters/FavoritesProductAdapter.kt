package com.example.fakestore.ui.fragment.favorites.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.data.models.response.Product
import com.example.fakestore.databinding.ItemProductInCategoryBinding
import com.example.fakestore.databinding.ItemProductInCategoryShimmerBinding

private const val SHIMMER_TYPE = 0
private const val PRODUCT_TYPE = 1

class FavoritesProductAdapter(private val interaction: Interaction? = null) :
    ListAdapter<Product, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    var isLoading = true

    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>() {

            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem == newItem
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == PRODUCT_TYPE) {
            FavoritesProductViewHolder.from(parent, interaction = interaction)
        } else {
            FavoritesProductShimmerViewHolder.from(parent)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FavoritesProductViewHolder -> {
                val item = getItem(position)
                holder.onBind(item)
            }
            is FavoritesProductShimmerViewHolder -> {
                holder.onBind(isLoading)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (isLoading) 7 else super.getItemCount()
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoading) {
            SHIMMER_TYPE
        } else {
            PRODUCT_TYPE
        }
    }

    class FavoritesProductViewHolder constructor(
        private val binding: ItemProductInCategoryBinding,
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
            fun from(viewGroup: ViewGroup, interaction: Interaction?): FavoritesProductViewHolder {
                val bind = ItemProductInCategoryBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
                return FavoritesProductViewHolder(bind, interaction = interaction)
            }
        }

    }


    class FavoritesProductShimmerViewHolder constructor(
        private val binding: ItemProductInCategoryShimmerBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(startShimmer: Boolean) {
            if (startShimmer) {
                binding.shimmerLayout.startShimmer()
            } else {
                binding.shimmerLayout.stopShimmer()
                binding.shimmerLayout.setShimmer(null)
            }
        }

        companion object {
            fun from(viewGroup: ViewGroup): FavoritesProductShimmerViewHolder {
                val bind = ItemProductInCategoryShimmerBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
                return FavoritesProductShimmerViewHolder(bind)
            }
        }


    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Product)
    }
}