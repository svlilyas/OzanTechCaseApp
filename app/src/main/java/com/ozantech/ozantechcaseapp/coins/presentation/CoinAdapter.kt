package com.ozantech.ozantechcaseapp.coins.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ozantech.ozantechcaseapp.coins.domain.CoinListViewModel
import com.ozantech.ozantechcaseapp.core.model.extension.CollectionExt.safeGet
import com.ozantech.ozantechcaseapp.core.model.remote.response.CoinResponse
import com.ozantech.ozantechcaseapp.core.uicomponents.extensions.ViewExt.setOnDebouncedClickListener
import com.ozantech.ozantechcaseapp.core.uicomponents.platform.BaseViewHolder
import com.ozantech.ozantechcaseapp.databinding.ItemCoinBinding
import com.ozantech.ozantechcaseapp.databinding.ItemCoinHeaderBinding
import com.ozantech.ozantechcaseapp.databinding.ItemCoinSeparatorBinding

class CoinAdapter(private val viewModel: CoinListViewModel) :
    PagingDataAdapter<CoinListItem, RecyclerView.ViewHolder>(
        differCallBack
    ) {
    var debouncedClickListener: ((item: CoinResponse.Coin) -> Unit)? = null

    fun setOnDebouncedClickListener(listener: (item: CoinResponse.Coin) -> Unit) {
        debouncedClickListener = listener
    }

    override fun getItemViewType(position: Int): Int {
        snapshot().items.safeGet(index = position)?.let {
            return when (it) {
                CoinListItem.Header -> PairListViewType.Header.ordinal
                is CoinListItem.CoinItem -> PairListViewType.CoinItem.ordinal
                CoinListItem.Separator -> PairListViewType.Separator.ordinal
            }
        }
        return PairListViewType.Separator.ordinal
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        snapshot().items.safeGet(index = position)?.let {
            when (it) {
                CoinListItem.Header -> (holder as CoinHeaderViewHolder).bind()
                is CoinListItem.CoinItem -> (holder as CoinViewHolder).bind(
                    coinItem = it.coinItem
                )

                CoinListItem.Separator -> (holder as CoinSeparatorViewHolder).bind()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            PairListViewType.Header.ordinal -> CoinHeaderViewHolder(parent = parent)
            PairListViewType.CoinItem.ordinal -> CoinViewHolder(parent = parent)
            else -> CoinSeparatorViewHolder(parent = parent)
        }

    inner class CoinViewHolder(
        parent: ViewGroup
    ) : BaseViewHolder<ItemCoinBinding>(
        binding = ItemCoinBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    ) {
        fun bind(coinItem: CoinResponse.Coin) {
            with(binding) {
                item = coinItem
                ivFavorite.setOnDebouncedClickListener {
                    val newCoinItem=coinItem.copy(isFavorite = !coinItem.isFavorite)
                    item = newCoinItem
                    viewModel.toggleFavorite(coinItem = newCoinItem)
                    executePendingBindings()
                }
                root.setOnDebouncedClickListener {
                    debouncedClickListener?.invoke(coinItem)
                }
                executePendingBindings()
            }
        }
    }

    inner class CoinHeaderViewHolder(
        parent: ViewGroup
    ) : BaseViewHolder<ItemCoinHeaderBinding>(
        binding = ItemCoinHeaderBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    ) {
        fun bind() {}
    }

    inner class CoinSeparatorViewHolder(
        parent: ViewGroup
    ) : BaseViewHolder<ItemCoinSeparatorBinding>(
        binding = ItemCoinSeparatorBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    ) {
        fun bind() {}
    }

    companion object {
        enum class PairListViewType {
            Header, CoinItem, Separator
        }

        private val differCallBack = object : DiffUtil.ItemCallback<CoinListItem>() {
            override fun areItemsTheSame(
                oldItem: CoinListItem, newItem: CoinListItem
            ) = oldItem is CoinListItem.CoinItem && newItem is CoinListItem.CoinItem &&
                    oldItem.coinItem.uuid == newItem.coinItem.uuid

            override fun areContentsTheSame(
                oldItem: CoinListItem, newItem: CoinListItem
            ) = oldItem == newItem
        }
    }
}