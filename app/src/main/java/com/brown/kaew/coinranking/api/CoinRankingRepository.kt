package com.brown.kaew.coinranking.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brown.kaew.coinranking.data.Coin

class CoinRankingRepository(private val service: CoinRankingService) {

    // keep the list of all results received
    private val inMemoryCache = mutableListOf<Coin>()

    suspend fun getCoinsByRange(offset: Int, limit: Int): List<Coin> {
        inMemoryCache.addAll(service.getCoinsByRange(offset, limit).data.coins)
        return inMemoryCache
    }

    suspend fun searchCoinWithFilter(filter: String): List<Coin> {
        clearInMemoryCache()
        return service.searchCoinWithFilter(filter).data.coins
    }

    suspend fun getAllCoins(): List<Coin> {
        clearInMemoryCache()
        return service.getCoins().data.coins
    }

    fun clearInMemoryCache(): List<Coin> {
        inMemoryCache.clear()
        return inMemoryCache
    }
}