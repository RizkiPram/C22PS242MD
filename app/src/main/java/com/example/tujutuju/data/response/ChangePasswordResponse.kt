package com.example.tujutuju.data.response

import com.google.gson.annotations.SerializedName

data class ChangePasswordResponse(

	@field:SerializedName("meta")
	val dataChangePassword: DataChangePassword
)

data class DataChangePassword(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("message")
	val message: String
)
