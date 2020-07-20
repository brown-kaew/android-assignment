package com.brown.kaew.coinranking

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.brown.kaew.coinranking.api.CoinRankingRepository
import com.brown.kaew.coinranking.api.CoinRankingService
import com.brown.kaew.coinranking.ui.ViewModelFactory

object Injector {

    //view model
    fun provideMainViewModelFactory(): ViewModelProvider.Factory {
        val repository = CoinRankingRepository(CoinRankingService.create())
        return ViewModelFactory(repository)
    }

}