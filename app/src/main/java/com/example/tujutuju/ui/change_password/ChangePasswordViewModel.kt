package com.example.tujutuju.ui.change_password

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tujutuju.LoginInformation
import com.example.tujutuju.api.ApiConfig
import com.example.tujutuju.data.response.ChangePasswordResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordViewModel:ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message =MutableLiveData<String>()
    val message:LiveData<String> = _message

    fun changePassword(token:String,oldPassword:String,newPassword:String){
        _isLoading.value=true
        val userInfo=LoginInformation(old_password = oldPassword,
                                        new_password = newPassword)
        val client = ApiConfig.getApiService().changePassword("Bearer $token",userInfo)
        client.enqueue(object : Callback<ChangePasswordResponse>{
            override fun onResponse(
                call: Call<ChangePasswordResponse>,
                response: Response<ChangePasswordResponse>
            ) {
                val responseBody=response.body()
                if (response.isSuccessful ){
                    _isLoading.value=false
                    if (responseBody != null) {
                        _message.value=responseBody.dataChangePassword.message
                    }else{
                        _message.value=responseBody?.dataChangePassword?.message
                    }
                }
            }

            override fun onFailure(call: Call<ChangePasswordResponse>, t: Throwable) {
                _isLoading.value=true
                _message.value=t.message
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
    companion object{
        private const val TAG = "ChangePasswordViewModel"
    }
}