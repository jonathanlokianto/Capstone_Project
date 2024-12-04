package com.dicoding.capstone.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ArticleResponse(

	@field:SerializedName("data")
	val data: List<DataItem>, // List tidak nullable

	@field:SerializedName("message")
	val message: String, // String tidak nullable jika selalu ada

	@field:SerializedName("status")
	val status: String // String tidak nullable jika selalu ada
)

@Parcelize
data class DataItem(

	@field:SerializedName("image_url")
	val imageUrl: String, // URL harus ada dan tidak nullable

	@field:SerializedName("id")
	val id: Int, // ID harus ada dan tidak nullable

	@field:SerializedName("title")
	val title: String, // Title harus ada dan tidak nullable

	@field:SerializedName("content")
	val content: String // Content harus ada dan tidak nullable
) : Parcelable
