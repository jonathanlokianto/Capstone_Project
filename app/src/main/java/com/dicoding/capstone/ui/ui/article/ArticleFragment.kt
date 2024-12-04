package com.dicoding.capstone.ui.ui.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.capstone.R

class ArticleFragment : Fragment() {

    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: View
    private val articleViewModel: ArticleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_article, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi Toolbar
        val toolbar: Toolbar = view.findViewById(R.id.my_toolbar)
        val toolbarTitle: TextView = toolbar.findViewById(R.id.toolbar_title)
        toolbarTitle.text = "Articles"
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)

        // Inisialisasi RecyclerView
        recyclerView = view.findViewById(R.id.rv_article)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        articleAdapter = ArticleAdapter()
        recyclerView.adapter = articleAdapter

        // Inisialisasi ProgressBar
        progressBar = view.findViewById(R.id.progressBar)

        // Observasi data dari ViewModel
        observeViewModel()
        articleViewModel.fetchArticles()
    }

    private fun observeViewModel() {
        articleViewModel.articles.observe(viewLifecycleOwner) { articles ->
            progressBar.visibility = View.GONE
            if (articles != null) {
                articleAdapter.submitList(articles)
            } else {
                Toast.makeText(requireContext(), "Failed to load articles", Toast.LENGTH_SHORT).show()
            }
        }

        articleViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}
