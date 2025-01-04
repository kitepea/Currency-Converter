package com.kitepea.currency_converter.viewmodel

import com.kitepea.currency_converter.helper.Resource
import com.kitepea.currency_converter.model.ApiResponse
import com.kitepea.currency_converter.network.ApiDataSource
import com.kitepea.currency_converter.network.BaseDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiDataSource: ApiDataSource) :
    BaseDataSource() {

    suspend fun getConvertedData(
        accessKey: String, from: String, to: String, amount: Double
    ): Flow<Resource<ApiResponse>> {
        return flow {
            emit(safeApiCall<ApiResponse> {
                apiDataSource.getConvertedRate(
                    accessKey,
                    from,
                    to,
                    amount
                )
            })
        }.flowOn(Dispatchers.IO) // for emitting the flow on IO dispatcher
    }
}