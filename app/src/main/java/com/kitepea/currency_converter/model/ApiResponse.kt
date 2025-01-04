package com.kitepea.currency_converter.model

data class ApiResponse(
    val amount: String,
    val baseCurrencyCode: String,
    val baseCurrencyName: String,
    var rates: HashMap<String, Rates> = HashMap(),
    val status: String,
    val updatedDate: String
)
