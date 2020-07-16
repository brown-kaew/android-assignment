package com.brown.kaew.coinranking.data

import com.google.gson.annotations.SerializedName

data class CoinRankingSearchResponse(
    @field:SerializedName("status") val status: String,
    @field:SerializedName("data") val data: Data
)