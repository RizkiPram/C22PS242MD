package com.example.tujutuju.ui.login

import android.util.Log
import androidx.lifecycle.*
import com.example.tujutuju.LoginInformation
import com.example.tujutuju.api.ApiConfig
import com.example.tujutuju.data.lokal.UserModel
import com.example.tujutuju.data.lokal.UserPreferences
import com.example.tujutuju.data.response.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref: UserPreferences) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(email:String,password:String){
        _isLoading.value=true
        val userInfo = LoginInformation(email = email,
                                        password=password)
        val client = ApiConfig.getApiService().login(userInfo)
        client.enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _isLoading.value=false
                val responseBody=response.body()
                if (response.isSuccessful){
                    if (responseBody != null){
                        val userModel=UserModel(
                            responseBody.data.user.name,
                            email,
                            password,
                            responseBody.data.user.phone,
                            responseBody.data.user.avatar,
                            responseBody.data.token,
                            true
                        )
                        saveUser(userModel)
                        _isLoading.value=false
                    }
                }else{
                    Log.e(TAG,"onFailure: ${response.message()}" )
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value=false
                Log.e(TAG,"onFailure: ${t.message}")
            }
        })
    }

    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }
    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }
    companion object {
        private const val TAG = "LoginViewModel"
    }
}