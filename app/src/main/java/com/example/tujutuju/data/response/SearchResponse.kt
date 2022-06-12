package com.example.tujutuju.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class SearchResponse(

	@field:SerializedName("data")
	val searchData: SearchData,

	@field:SerializedName("meta")
	val meta: Meta
)

data class SearchData(

	@field:SerializedName("places")
	val places: List<PlacesItem>,

	@field:SerializedName("foods")
	val foods: List<FoodsItem>,

	@field:SerializedName("restaurants")
	val restaurants: List<RestaurantsItem>
)
@Parcelize
data class PlacesItem(

	@field:SerializedName("images")
	val images: List<String>,

	@field:SerializedName("address")
	val address: String,

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
) : Parcelable

data class RestaurantsItem(

	@field:SerializedName("images")
	val images: List<String>,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("phone")
	val phone: String,

	@field:SerializedName("review")
	val searchReview: SearchReview,

	@field:SerializedName("latitude")
	val latitude: Double,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("longitude")
	val longitude: Double
)


data class FoodsItem(

	@field:SerializedName("images")
	val images: List<String>,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: Int
)

data class SearchReview(

	@field:SerializedName("average_rating")
	val averageRating: Double
)
