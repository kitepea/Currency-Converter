package com.kitepea.currency_converter.model

data class Rates(
    val currencyName: String,
    val rate: String,
    val rateForAmount: Double
)
