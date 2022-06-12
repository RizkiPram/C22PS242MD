package com.example.tujutuju.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.example.tujutuju.api.ApiConfig
import com.example.tujutuju.data.lokal.UserModel
import com.example.tujutuju.data.lokal.UserPreferences
import com.example.tujutuju.data.response.PlacesItem
import com.example.tujutuju.data.response.SearchResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: UserPreferences):ViewModel() {
    private val _place = MutableLiveData<List<PlacesItem>>()
    val place: LiveData<List<PlacesItem>> = _place

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setSearch(q:String){
        _isLoading.value=true
        val client = ApiConfig.getApiService().search(q)
        client.enqueue(object : Callback<SearchResponse>{
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                _isLoading.value=false
                if (response.isSuccessful){
                    _place.value =response.body()?.searchData?.places
                }else{
                    Log.e(TAG,"onFailure:${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value=true
                Log.e(TAG,"onFailure:${t.message}")
            }
        })
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