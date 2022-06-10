package com.example.tujutuju.api

import android.service.autofill.UserData
import com.example.tujutuju.LoginInformation
import com.example.tujutuju.data.response.LoginResponse
import com.example.tujutuju.data.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.*


interface ApiService {


    @POST("auth/login")
    fun login(@Body userInfo: LoginInformation): Call<LoginResponse>


    @POST("auth/register")
    fun register(@Body useInfo:LoginInformation):Call<RegisterResponse>

    @GET("search")
    fun search()
}