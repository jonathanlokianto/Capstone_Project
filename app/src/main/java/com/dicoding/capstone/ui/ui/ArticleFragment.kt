package com.dicoding.capstone.ui.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.capstone.R
import com.dicoding.capstone.data.retrofit.RetrofitClient
import com.dicoding.capstone.ui.ArticleAdapter
import kotlinx.coroutines.launch

class ArticleFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_article, container, false)
        recyclerView = view.findViewById(R.id.rvArticle) // ID harus sesuai
        progressBar = view.findViewById(R.id.progressBar)

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        fetchArticles()

        return view
    }

    private fun fetchArticles() {
        progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.getArticles()
                val articles = response.data?.filterNotNull() ?: emptyList()

                if (articles.isNotEmpty()) {
                    recyclerView.adapter = ArticleAdapter(
                        articles = articles,
                        onClick = { article ->
                            val bundle = Bundle().apply {
                                putString("id", article.id.toString())
                            }
                            findNavController().navigate(R.id.action_nav_article_to_nav_detail_article, bundle)
                        },
                        isHorizontal = false
                    )
                } else {
                    Toast.makeText(context, "No articles available", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("ArticleFragment", "Error fetching articles", e)
                Toast.makeText(context, "Failed to load articles", Toast.LENGTH_SHORT).show()
            } finally {
                progressBar.visibility = View.GONE
            }
        }
    }
}
