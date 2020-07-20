package com.brown.kaew.coinranking.data

import com.google.gson.annotations.SerializedName

data class Stats(
    @field:SerializedName("total") val total: Int,
    @field:SerializedName("offset") val offset: Int,
    @field:SerializedName("limit") val limit: Int,
    @field:SerializedName("order") val order: String,
    @field:SerializedName("base") val base: String,
    @field:SerializedName("totalMarkets") val totalMarkets: Int,
    @field:SerializedName("totalExchanges") val totalExchanges: Int,
    @field:SerializedName("totalMarketCap") val totalMarketCap: Double,
    @field:SerializedName("total24hVolume") val total24hVolume: Double
)