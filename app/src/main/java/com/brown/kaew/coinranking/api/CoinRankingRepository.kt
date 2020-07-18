package com.brown.kaew.coinranking.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brown.kaew.coinranking.data.Coin

class CoinRankingRepository(private val service: CoinRankingService) {

    // keep the list of all results received
    private val inMemoryCache = mutableListOf<Coin>()

    suspend fun loadCoins() {
//        inMemoryCache.clear()
        inMemoryCache.addAll(service.getCoins().data.coins)
        Log.d(this.javaClass.name,"load coin size ${inMemoryCache.size}")
    }

    fun getAllCoins(): LiveData<List<Coin>> {
        val list:LiveData<List<Coin>> = MutableLiveData(inMemoryCache)
        Log.d(this.javaClass.name,"get Live Data list of coins")
        return list
    }
}