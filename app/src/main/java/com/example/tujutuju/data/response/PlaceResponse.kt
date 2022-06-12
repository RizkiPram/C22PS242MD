package com.example.tujutuju.data.response

import com.google.gson.annotations.SerializedName

data class PlaceResponse(

    @field:SerializedName("pagination")
	val pagination: Pagination,

    @field:SerializedName("data")
	val data: List<SearchItem>,

    @field:SerializedName("meta")
	val dataChangePassword: DataChangePassword
)

data class Review(

	@field:SerializedName("average_rating")
	val averageRating: Double
)

data class DataItem(

	@field:SerializedName("images")
	val images: List<String>,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("review")
	val review: Review,

	@field:SerializedName("latitude")
	val latitude: Double,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("longitude")
	val longitude: Double
)

data class Pagination(

	@field:SerializedName("next_page")
	val nextPage: String,

	@field:SerializedName("total_data")
	val totalData: Int,

	@field:SerializedName("total_page")
	val totalPage: Int,

	@field:SerializedName("prev_page")
	val prevPage: String
)
