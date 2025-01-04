package com.kitepea.currency_converter.network

import javax.inject.Inject

/*
* This class needs to be managed by Hilt.
* When we need to create an instance of ApiDataSource, Hilt will automatically resolve the dependencies declared in the constructor of the class (here is ApiService).
*
* This class exposes the ApiService interface so we can use it in our repository.
* */
class ApiDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun getConvertedRate(accessKey: String, from: String, to: String, amount: Double) =
        apiService.convertCurrencyCall(accessKey, from, to, amount)
}