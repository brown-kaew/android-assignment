package com.brown.kaew.coinranking.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinRankingService {

    @GET("v1/public/coins")
    suspend fun getCoins(): CoinRankingSearchResponse

    @GET("/v1/public/coins")
    suspend fun getCoinsByRange(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): CoinRankingSearchResponse

    @GET("/v1/public/coins")
    suspend fun searchCoinWithFilter(
        @Query("prefix") filter: String
    ): CoinRankingSearchResponse


    companion object {
        private const val BASE_URL = "https://api.coinranking.com/"

        fun create(): CoinRankingService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CoinRankingService::class.java)
        }
    }
}