package com.dicoding.capstone.ui.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.capstone.api.ApiConfig
import com.dicoding.capstone.response.DataItem
import kotlinx.coroutines.launch

class ArticleViewModel : ViewModel() {

    private val _articles = MutableLiveData<List<DataItem>?>()
    val articles: LiveData<List<DataItem>?> = _articles

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchArticles() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().getArticles()
                if (response.isSuccessful) {
                    _articles.value = response.body()?.data // Ambil data dari respons
                } else {
                    _articles.value = null
                }
            } catch (e: Exception) {
                _articles.value = null // Tangani jika ada error
            } finally {
                _isLoading.value = false
            }
        }
    }
}
