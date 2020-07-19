package com.brown.kaew.coinranking.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brown.kaew.coinranking.api.CoinRankingRepository
import com.brown.kaew.coinranking.data.Coin
import kotlinx.coroutines.launch

class MainViewModel(private val repository: CoinRankingRepository) : ViewModel() {

    private val TAG = javaClass.simpleName

    private val VISIBLE_THRESHOLD = 5
    private val _limit = 10
    private var _offset = 0
    private var getMoreCoinsInProgress = false;

    private val _coins = MutableLiveData<List<Coin>>()

    fun getCoins() = _coins

    fun loadCoins() {
        viewModelScope.launch {
            _coins.postValue(repository.getAllCoins())
        }
    }

    fun refresh() {
        _offset = 0
        _coins.postValue(repository.clearInMemoryCache())
        firstLoadCoins()
    }

    fun firstLoadCoins() {
        getMoreCoins(_limit*2)
    }

    private fun getMoreCoins(limit: Int) {
        if (getMoreCoinsInProgress) return

        getMoreCoinsInProgress = true
        viewModelScope.launch {
            Log.d(TAG, "load more")
            _coins.postValue(repository.getCoinsByRange(_offset, limit))
            _offset += limit
            getMoreCoinsInProgress = false
            Log.d(TAG, "load more done")
        }
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        Log.d(
            TAG,
            "visibleItemCount$visibleItemCount lastVisibleItemPosition$lastVisibleItemPosition totalItemCount$totalItemCount"
        )
        val diffOfTotalAndLastVisible = totalItemCount - lastVisibleItemPosition

        val needLoad = when {
//            diffOfTotalAndLastVisible <= 1 -> true
            diffOfTotalAndLastVisible <= visibleItemCount -> true
            else -> false
        }
        if (needLoad) {
            getMoreCoins(_limit)
        }
    }

}