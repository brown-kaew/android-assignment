package com.brown.kaew.coinranking.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brown.kaew.coinranking.api.CoinRankingRepository
import com.brown.kaew.coinranking.data.Coin
import kotlinx.coroutines.launch

class MainViewModel(private val repository: CoinRankingRepository) : ViewModel() {
    // TODO: Implement the ViewModel

    fun getCoins() = repository.getAllCoins()

    fun loadCoins() {
        viewModelScope.launch {
            repository.loadCoins()
        }
    }

}