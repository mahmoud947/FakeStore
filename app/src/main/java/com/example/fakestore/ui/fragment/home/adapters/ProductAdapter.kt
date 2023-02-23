package com.example.fakestore.ui.fragment.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.data.models.response.Product
import com.example.fakestore.databinding.ItemProductBinding
import com.example.fakestore.databinding.ItemProductShimmerBinding


private const val SHIMMER_TYPE = 0
private const val PRODUCT_TYPE = 1

class ProductAdapter(private val interaction: Interaction? = null) :
    ListAdapter<Product, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    var isLoading = true

    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>() {

            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem.isFavorite == newItem.isFavorite &&
                        oldItem.images == newItem.images &&
                        oldItem.category == newItem.category
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == PRODUCT_TYPE) {
            ProductViewHolder.from(parent, interaction = interaction)
        } else {
            ProductShimmerViewHolder.from(parent)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProductViewHolder -> {
                val item = getItem(position)
                holder.onBind(item)
            }
            is ProductShimmerViewHolder -> {
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

    class ProductViewHolder constructor(
        private val binding: ItemProductBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: Product) {
            binding.product = item
            binding.executePendingBindings()
            binding.cvProduct.setOnClickListener {
                interaction?.onItemSelected(bindingAdapterPosition, item)
            }
            binding.cvFavorite.setOnClickListener {
                interaction?.onAddToFavoriteClick(item)
            }
        }

        companion object {
            fun from(viewGroup: ViewGroup, interaction: Interaction?): ProductViewHolder {
                val bind = ItemProductBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
                return ProductViewHolder(bind, interaction = interaction)
            }
        }
    }


    class ProductShimmerViewHolder constructor(
        private val binding: ItemProductShimmerBinding,
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
            fun from(viewGroup: ViewGroup): ProductShimmerViewHolder {
                val bind = ItemProductShimmerBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
                return ProductShimmerViewHolder(bind)
            }
        }

    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Product)
        fun onAddToFavoriteClick(item: Product)
    }
}