package com.example.fakestore.ui.fragment.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.data.models.response.Product
import com.example.fakestore.databinding.ItemProductHomeGridBinding
import com.example.fakestore.databinding.RecyclerViewFooterBinding

private const val SHIMMER_TYPE = 0
private const val PRODUCT_TYPE = 1

class PagingProductAdapter(
    private val onItemClicked: OnItemClicked
) : PagingDataAdapter<Product, PagingProductAdapter.ProductViewHolder>(PRODUCT_DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder.from(parent, onItemClicked)
    }


    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        getItem(position)?.let {
            holder.onBind(it)
        }
    }

    companion object {
        val PRODUCT_DIFF_UTIL: DiffUtil.ItemCallback<Product> =
            object : DiffUtil.ItemCallback<Product>() {
                override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
                    oldItem == newItem
            }
    }

    class ProductViewHolder(
        private val binding: ItemProductHomeGridBinding,
        private val onItemClicked: OnItemClicked
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(product: Product) {
            binding.product = product
            binding.root.setOnClickListener {
                onItemClicked.onItemClicked(product, this.bindingAdapterPosition)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(viewGroup: ViewGroup, onItemClicked: OnItemClicked): ProductViewHolder {
                val bind =
                    ItemProductHomeGridBinding.inflate(
                        LayoutInflater.from(viewGroup.context),
                        viewGroup,
                        false
                    )
                return ProductViewHolder(bind, onItemClicked)
            }
        }
    }


    interface OnItemClicked {
        fun onItemClicked(product: Product, oldPosition: Int)
    }
}


class HeaderFooterAdapter(
    private val onRetryClicked: OnRetryClicked
) : LoadStateAdapter<HeaderFooterAdapter.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.onBind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        return ViewHolder.from(parent, onRetryClicked)
    }

    class ViewHolder(
        private val binding: RecyclerViewFooterBinding,
        private val onRetryClicked: OnRetryClicked
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(loadState: LoadState) {
            binding.loadState = loadState
            binding.retryButton.setOnClickListener {
                onRetryClicked.onRetryClicked()
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(viewGroup: ViewGroup, onRetryClicked: OnRetryClicked): ViewHolder {
                val binding = RecyclerViewFooterBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
                return ViewHolder(binding, onRetryClicked)
            }
        }

    }

    interface OnRetryClicked {
        fun onRetryClicked()
    }

}
