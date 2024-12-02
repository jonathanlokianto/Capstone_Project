package com.dicoding.capstone.data.retrofit

import com.dicoding.capstone.data.model.Article
import com.dicoding.capstone.data.model.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("articles")
    suspend fun getArticles(): Response

    @GET("articles/{id}")
    suspend fun getArticleDetail(@Path("id") id: String): Article
}

object RetrofitClient {
    private const val BASE_URL = "https://wired-victor-441608-s8.et.r.appspot.com/"

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
