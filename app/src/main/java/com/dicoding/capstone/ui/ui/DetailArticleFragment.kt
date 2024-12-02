package com.dicoding.capstone.ui.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.dicoding.capstone.R
import com.dicoding.capstone.data.retrofit.RetrofitClient
import kotlinx.coroutines.launch

class DetailArticleFragment : Fragment() {
    private lateinit var progressBar: ProgressBar
    private lateinit var imageView: ImageView
    private lateinit var titleView: TextView
    private lateinit var descriptionView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_article, container, false)
        progressBar = view.findViewById(R.id.progress_bar)
        imageView = view.findViewById(R.id.ivMediaCover)
        titleView = view.findViewById(R.id.tvName)
        descriptionView = view.findViewById(R.id.tvDescription)

        // Mendapatkan ID artikel dari arguments
        val articleId = arguments?.getString("id")
        if (articleId != null) {
            fetchArticleDetail(articleId)
        } else {
            Toast.makeText(context, "Article ID is missing", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun fetchArticleDetail(id: String) {
        progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                // Pastikan API mengembalikan response yang sesuai
                val response = RetrofitClient.instance.getArticleDetail(id)

                // Periksa jika response null atau tidak sesuai
                if (response != null) {
                    titleView.text = response.title
                    descriptionView.text = response.content
                    Glide.with(imageView.context)
                        .load(response.imageUrl)
                        .into(imageView)
                } else {
                    Toast.makeText(context, "Article not found", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // Menangani error
                Log.e("DetailArticleFragment", "Error fetching article detail", e)
                Toast.makeText(context, "Failed to load article detail", Toast.LENGTH_SHORT).show()
            } finally {
                progressBar.visibility = View.GONE
            }
        }
    }
}
