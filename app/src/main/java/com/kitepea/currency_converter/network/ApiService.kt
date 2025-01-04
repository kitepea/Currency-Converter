package com.kitepea.currency_converter.network

import com.kitepea.currency_converter.model.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("convert")
    suspend fun convertCurrencyCall(
        @Query("api_key") accessKey: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Double
    ): Response<ApiResponse>

}