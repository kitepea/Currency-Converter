package com.kitepea.currency_converter.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kitepea.currency_converter.helper.Resource
import com.kitepea.currency_converter.helper.SingleLiveEvent
import com.kitepea.currency_converter.model.ApiResponse
import com.kitepea.currency_converter.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepo: MainRepository) :
    ViewModel() {

    private val _data = SingleLiveEvent<Resource<ApiResponse>>()

    val data = _data
    val convertedRate = MutableLiveData<Double>()

    // Public function to get the result of conversion
    fun getConvertedData(accessKey: String, from: String, to: String, amount: Double) {
        viewModelScope.launch {
            mainRepo.getConvertedData(accessKey, from, to, amount).collect {
                data.value = it
            }
        }
    }
}