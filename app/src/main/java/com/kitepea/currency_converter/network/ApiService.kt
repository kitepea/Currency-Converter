package com.kitepea.currency_converter.network

import com.kitepea.currency_converter.helper.EndPoints
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(EndPoints.CONVERT_URL)
    suspend fun convertCurrencyCall(
        @Query("api_key") accessKey: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Double
    ): Response<ApiResponse>

}