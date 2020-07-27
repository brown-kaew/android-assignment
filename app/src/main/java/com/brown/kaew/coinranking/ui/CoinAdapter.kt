package com.brown.kaew.coinranking.ui

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brown.kaew.coinranking.R
import com.brown.kaew.coinranking.data.Coin

class CoinAdapter(var coinList: List<Coin>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = javaClass.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.special_coin_view_item -> SpecialCoinViewHolder.create(parent, viewType)
            else -> NormalCoinViewHolder.create(parent, viewType)
        }
    }

    override fun getItemCount(): Int {
        return coinList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            R.layout.special_coin_view_item -> (holder as SpecialCoinViewHolder).bind(coinList[position])
            else -> (holder as NormalCoinViewHolder).bind(coinList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position % 5 == 4 -> R.layout.special_coin_view_item
            else -> R.layout.normal_coin_view_item
        }
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        Log.d(TAG, "onViewRecycled : ${holder.itemViewType}")
    }
}
