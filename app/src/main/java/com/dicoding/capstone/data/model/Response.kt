package com.dicoding.capstone.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Response(
	val data: List<Article?>? = emptyList() ,
	val message: String? = null,
	val status: String? = null
) : Parcelable

@Parcelize
data class Article(
	val id: Int? = null,
	val title: String? = null,
	val content: String? = null,
	@SerializedName("image_url")
	val imageUrl: String? = null  // Gunakan SerializedName jika key berbeda
) : Parcelable

