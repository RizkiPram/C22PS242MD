package com.example.tujutuju.data.lokal

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val name: String,
    val email: String,
    val password: String,
    val phone:String,
    val avatar : String,
    val token: String,
    val isLogin: Boolean
): Parcelable