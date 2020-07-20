package com.brown.kaew.coinranking.data

import com.google.gson.annotations.SerializedName

data class Data(
    @field:SerializedName("stats") val stats: Stats,
    @field:SerializedName("base") val base: Base,
    @field:SerializedName("coins") val coins: List<Coin>
)