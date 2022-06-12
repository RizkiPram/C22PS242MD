package com.example.tujutuju.api

import android.service.autofill.UserData
import com.example.tujutuju.LoginInformation
import com.example.tujutuju.data.response.ChangePasswordResponse
import com.example.tujutuju.data.response.LoginResponse
import com.example.tujutuju.data.response.RegisterResponse
import com.example.tujutuju.data.response.SearchResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*


interface ApiService {


    @POST("auth/login")
    fun login(@Body userInfo: LoginInformation): Call<LoginResponse>


    @POST("auth/register")
    fun register(@Body useInfo:LoginInformation):Call<RegisterResponse>

    @GET("search")
    fun search(@Query ("q")id:String):Call<SearchResponse>

    @Multipart
    @POST("me/avatar")
    fun changeProfilePicture(
        @Header("Authorization") token:String,
        @Part file: MultipartBody.Part
    ):Call<LoginResponse>

    @PUT("me/password")
    fun changePassword(
        @Header("Authorization") token:String,
        @Body userInfo: LoginInformation
    ):Call<ChangePasswordResponse>
}