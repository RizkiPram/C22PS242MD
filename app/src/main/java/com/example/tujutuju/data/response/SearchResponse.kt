package com.example.tujutuju.data.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(

	@field:SerializedName("pagination")
	val pagination: Pagination,

	@field:SerializedName("data")
	val data: List<SearchItem>,

	@field:SerializedName("meta")
	val meta: SearchMeta
)

data class ReviewPlace(

	@field:SerializedName("average_rating")
	val averageRating: Double

)

data class SearchMeta(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("message")
	val message: String
)

data class SearchItem(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("review")
	val review: ReviewPlace,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: Int
)

