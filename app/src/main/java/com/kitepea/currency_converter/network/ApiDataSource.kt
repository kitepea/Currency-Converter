package com.kitepea.currency_converter.network

import javax.inject.Inject

class ApiDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun getConvertedRate(accessKey: String, from: String, to: String, amount: Double) =
        apiService.convertCurrencyCall(accessKey, from, to, amount)
}