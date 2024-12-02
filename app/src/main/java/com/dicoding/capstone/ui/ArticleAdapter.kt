package com.dicoding.capstone.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.capstone.R
import com.dicoding.capstone.data.model.Article

class ArticleAdapter(
    private val articles: List<Article>,
    private val onClick: (Article) -> Unit,
    private val isHorizontal: Boolean
) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val layoutId = if (isHorizontal) R.layout.article_layout_horizontal else R.layout.article_layout
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int = articles.size

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleView: TextView = itemView.findViewById(R.id.tvArticleName)
        private val imageView: ImageView = itemView.findViewById(R.id.tvArticlePicture)

        fun bind(article: Article) {
            titleView.text = article.title

            if (!article.imageUrl.isNullOrEmpty()) {
                Glide.with(itemView.context)
                    .load(article.imageUrl)
                    .into(imageView)
            } else {
                // Atur placeholder jika URL gambar kosong
                Glide.with(itemView.context)
                    .load(R.drawable.logocapstone) //ini placeholder tok
                    .into(imageView)
            }

            itemView.setOnClickListener {
                onClick(article)
            }
        }

    }
}
