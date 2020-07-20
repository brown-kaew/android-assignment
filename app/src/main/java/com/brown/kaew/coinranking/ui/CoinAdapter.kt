package com.brown.kaew.coinranking.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.brown.kaew.coinranking.data.Coin

class CoinAdapter : ListAdapter<Coin, CoinViewHolder>(COIN_COMPARATOR) {

    companion object {

        private val COIN_COMPARATOR = object : DiffUtil.ItemCallback<Coin>() {
            override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        return CoinViewHolder.create(parent, viewType)
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position % 5 == 4 -> CoinViewHolder.Type.SPECIAL.ordinal
            else -> CoinViewHolder.Type.NORMAL.ordinal
        }

    }
}
