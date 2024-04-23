package com.ozantech.ozantechcaseapp.coins.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ozantech.ozantechcaseapp.core.model.remote.response.CoinResponse
import com.ozantech.ozantechcaseapp.core.uicomponents.extensions.ViewExt.setOnDebouncedClickListener
import com.ozantech.ozantechcaseapp.core.uicomponents.platform.BaseViewHolder
import com.ozantech.ozantechcaseapp.databinding.ItemFavoriteCoinBinding
import com.ozantech.ozantechcaseapp.databinding.ItemFavoriteCoinHeaderBinding

class FavoriteCoinsAdapter :
    ListAdapter<CoinResponse.Coin, RecyclerView.ViewHolder>(
        differCallBack
    ) {

    var debouncedClickListener: ((item: CoinResponse.Coin) -> Unit)? = null

    fun setOnDebouncedClickListener(listener: (item: CoinResponse.Coin) -> Unit) {
        debouncedClickListener = listener
    }

    override fun getItemViewType(position: Int): Int = if (position == 0) {
        FavoritePairViewType.HEADER.ordinal
    } else {
        FavoritePairViewType.FAVORITE_PAIR_ITEM.ordinal
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FavoritePairHeaderViewHolder -> holder.bind()
            is FavoritePairViewHolder -> holder.bind(getItem(position))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            FavoritePairViewType.HEADER.ordinal -> FavoritePairHeaderViewHolder(parent = parent)
            else -> FavoritePairViewHolder(parent = parent)
        }

    inner class FavoritePairViewHolder(
        parent: ViewGroup
    ) : BaseViewHolder<ItemFavoriteCoinBinding>(
        binding = ItemFavoriteCoinBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    ) {
        fun bind(pairEntity: CoinResponse.Coin) {
            with(binding) {
                //item = pairEntity
                executePendingBindings()

                root.setOnDebouncedClickListener {
                    debouncedClickListener?.invoke(pairEntity)
                }
            }
        }
    }

    inner class FavoritePairHeaderViewHolder(
        parent: ViewGroup
    ) : BaseViewHolder<ItemFavoriteCoinHeaderBinding>(
        binding = ItemFavoriteCoinHeaderBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    ) {
        fun bind() {}
    }

    companion object {
        enum class FavoritePairViewType {
            HEADER, FAVORITE_PAIR_ITEM
        }

        private val differCallBack = object : DiffUtil.ItemCallback<CoinResponse.Coin>() {
            override fun areItemsTheSame(
                oldItem: CoinResponse.Coin, newItem: CoinResponse.Coin
            ) = oldItem.uuid == newItem.uuid

            override fun areContentsTheSame(
                oldItem: CoinResponse.Coin, newItem: CoinResponse.Coin
            ) = oldItem == newItem
        }
    }
}