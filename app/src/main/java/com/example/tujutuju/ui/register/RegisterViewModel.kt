package com.example.tujutuju.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tujutuju.LoginInformation
import com.example.tujutuju.api.ApiConfig
import com.example.tujutuju.data.response.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel: ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message= MutableLiveData<String>()
    val message: LiveData<String> = _message
    fun register(name:String,email:String,password:String){
        _isLoading.value=true
        val userInfo=LoginInformation(name=name,email=email,password=password)
        val client =ApiConfig.getApiService().register(userInfo)
        client.enqueue(object : Callback<RegisterResponse>{
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                val responseBody=response.body()
                if (response.isSuccessful && responseBody!=null){
                    _isLoading.value=false
                    Log.e(TAG,"onSuccess: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value=false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
    companion object{
        private const val TAG = "RegisterViewModel"
    }
}