package com.example.tujutuju.ui.main

import androidx.lifecycle.*
import com.example.tujutuju.api.ApiConfig
import com.example.tujutuju.data.lokal.UserModel
import com.example.tujutuju.data.lokal.UserPreferences
import com.example.tujutuju.data.response.DataItem
import kotlinx.coroutines.launch

class MainViewModel(private val pref: UserPreferences):ViewModel() {
    private val _place = MutableLiveData<List<DataItem>>()
    val place: LiveData<List<DataItem>> = _place

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setSearch(query:String){
        _isLoading.value=true
        val client = ApiConfig.getApiService()
    }
    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }
    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }
    companion object{
        private const val TAG = "MainViewModel"
    }
}