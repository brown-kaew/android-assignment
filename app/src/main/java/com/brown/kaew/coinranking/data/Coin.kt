package com.brown.kaew.coinranking.data

import com.google.gson.annotations.SerializedName

data class Coin(
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("uuid") val uuid: String,
    @field:SerializedName("slug") val slug: String,
    @field:SerializedName("symbol") val symbol: String,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("description") val description: String,
    @field:SerializedName("color") val color: String,
    @field:SerializedName("iconType") val iconType: String,
    @field:SerializedName("iconUrl") val iconUrl: String,
    @field:SerializedName("websiteUrl") val websiteUrl: String,
    //    ArrayList < Object > socials = new ArrayList < Object > ();
    //    ArrayList < Object > links = new ArrayList < Object > ();
    @field:SerializedName("confirmedSupply") val confirmedSupply: Boolean,
    @field:SerializedName("numberOfMarkets") val numberOfMarkets: Int,
    @field:SerializedName("numberOfExchanges") val numberOfExchanges: Int,
    @field:SerializedName("type") val type: String,
    @field:SerializedName("volume") val volume: Long,
    @field:SerializedName("marketCap") val marketCap: Long,
    @field:SerializedName("price") val price: String,
    @field:SerializedName("circulatingSupply") val circulatingSupply: Double,
    @field:SerializedName("totalSupply") val totalSupply: Double,
    @field:SerializedName("approvedSupply") val approvedSupply: Boolean,
    @field:SerializedName("firstSeen") val firstSeen: Long,
    @field:SerializedName("change") val change: Double,
    @field:SerializedName("rank") val rank: Int,
    @field:SerializedName("history") val history: List<String>,
    //    AllTimeHigh AllTimeHighObject;
    @field:SerializedName("penalty") val penalty: Boolean
)