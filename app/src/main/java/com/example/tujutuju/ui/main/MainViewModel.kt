package com.example.tujutuju.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tujutuju.api.ApiConfig
import com.example.tujutuju.data.response.DataItem

class MainViewModel:ViewModel() {
    private val _place = MutableLiveData<List<DataItem>>()
    val place: LiveData<List<DataItem>> = _place

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setSearch(query:String){
        _isLoading.value=true
        val client = ApiConfig.getApiService()
    }

    companion object{
        private const val TAG = "MainViewModel"
    }
}