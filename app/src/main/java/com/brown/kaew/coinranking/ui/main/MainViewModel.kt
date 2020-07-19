package com.brown.kaew.coinranking.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brown.kaew.coinranking.api.CoinRankingRepository
import com.brown.kaew.coinranking.data.Coin
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(private val repository: CoinRankingRepository) : ViewModel() {
    enum class State {
        SEARCH,
        NORMAL
    }

    private val TAG = javaClass.simpleName

    private val VISIBLE_THRESHOLD = 5
    var state = State.NORMAL
    var lastSearch = ""
    private val _limit = 10
    private var _offset = 0
    private var getMoreCoinsInProgress = false;

    private var searchFilterJob: Job = Job()

    private val _coins = MutableLiveData<List<Coin>>()

    fun getCoins() = _coins

    fun loadCoins() {
        viewModelScope.launch {
            _coins.postValue(repository.getAllCoins())
        }
    }

    fun searchFilter(query: String) {
        if (state == State.SEARCH &&
            lastSearch != query &&
            query.trim().isNotEmpty()
        ) {
            Log.d(TAG, "searchFilter $query")
            searchFilterJob.cancel()
            _coins.postValue(repository.clearInMemoryCache())
            searchFilterJob = viewModelScope.launch {
                _coins.postValue(repository.searchCoinWithFilter(query.trim()))
                lastSearch = query
            }
        }
    }

    fun clearSearch() {
        Log.d(TAG, "clearSearch")
        // Set to default
        _coins.value = listOf()
        _offset = 0
        state = State.NORMAL
        getMoreCoins(_limit * 2)
    }

    fun refresh() {
        Log.d(TAG, "refresh")
        // Set to default
        _offset = 0
        state = State.NORMAL
        _coins.postValue(repository.clearInMemoryCache())
        getMoreCoins(_limit * 2)
    }

    fun firstLoadCoins() {
        if (_coins.value == null) {
            Log.d(TAG, "firstLoadCoins")
            getMoreCoins(_limit * 2)
        }
    }

    private fun getMoreCoins(limit: Int) {
        if (getMoreCoinsInProgress) return

        getMoreCoinsInProgress = true
        searchFilterJob.cancel()

        viewModelScope.launch {
            Log.d(TAG, "load more")
            _coins.postValue(repository.getCoinsByRange(_offset, limit))
            _offset += limit
            getMoreCoinsInProgress = false
            Log.d(TAG, "load more done")
        }
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
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