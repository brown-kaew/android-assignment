package com.brown.kaew.coinranking

import com.brown.kaew.coinranking.api.CoinRankingService
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking


fun main() {

    val service = CoinRankingService.create()

    runBlocking {
        val res = service.getCoins()
        val gson = Gson()
        val json = gson.toJson(res)

        println("Response: $json}")
    }


}

