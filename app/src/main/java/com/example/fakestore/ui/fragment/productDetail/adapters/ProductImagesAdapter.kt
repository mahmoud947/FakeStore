package com.example.fakestore.ui.fragment.productDetail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.databinding.ItemProductImageBinding
import com.example.fakestore.databinding.ItemProductImageShimmerBinding

private const val SHIMMER_TYPE = 0
private const val PRODUCT_TYPE = 1

class ProductImagesAdapter(private val interaction: Interaction? = null) :
    ListAdapter<String, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
    var isLoading = false

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {

            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem

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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == PRODUCT_TYPE) {
            ProductImagesViewHolder.from(parent, interaction = interaction)
        } else {
            ProductImagesShimmerViewHolder.from(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProductImagesViewHolder -> {
                val item = getItem(position)
                holder.onBind(item)
            }
            is ProductImagesShimmerViewHolder -> {
                holder.onBind(isLoading)
            }
        }
    }


    class ProductImagesViewHolder constructor(
        private val binding: ItemProductImageBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: String) {
            binding.imageUrl = item
            binding.root.setOnClickListener {
                interaction?.onItemSelected(bindingAdapterPosition, item)
            }
        }

        companion object {
            fun from(viewGroup: ViewGroup, interaction: Interaction?): ProductImagesViewHolder {
                val bind = ItemProductImageBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
                return ProductImagesViewHolder(bind, interaction = interaction)
            }
        }

    }

    class ProductImagesShimmerViewHolder constructor(
        private val binding: ItemProductImageShimmerBinding,
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
            fun from(viewGroup: ViewGroup): ProductImagesShimmerViewHolder {
                val bind = ItemProductImageShimmerBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
                return ProductImagesShimmerViewHolder(bind)
            }
        }


    }

    interface Interaction {
        fun onItemSelected(position: Int, item: String)
    }
}
