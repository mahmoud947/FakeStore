package com.example.fakestore.ui.fragment.productDetail.adapters

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

class SuggestionAdapter(private val interaction: Interaction? = null) :
    ListAdapter<Product, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    var isLoading = false

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
            SuggestionViewHolder.from(parent, interaction = interaction)
        } else {
            SuggestionShimmerViewHolder.from(parent)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SuggestionViewHolder -> {
                val item = getItem(position)
                holder.onBind(item)
            }
            is SuggestionShimmerViewHolder -> {
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

    class SuggestionViewHolder constructor(
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
            fun from(viewGroup: ViewGroup, interaction: Interaction?): SuggestionViewHolder {
                val bind = ItemProductBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
                return SuggestionViewHolder(bind, interaction = interaction)
            }
        }


    }


    class SuggestionShimmerViewHolder constructor(
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
            fun from(viewGroup: ViewGroup): SuggestionShimmerViewHolder {
                val bind = ItemProductShimmerBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
                return SuggestionShimmerViewHolder(bind)
            }
        }


    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Product)
    }
}