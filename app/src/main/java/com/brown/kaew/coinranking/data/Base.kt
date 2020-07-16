package com.brown.kaew.coinranking.data

import com.google.gson.annotations.SerializedName

data class Base(
    @field:SerializedName("symbol") val symbol: String,
    @field:SerializedName("sign") val sign: String
)