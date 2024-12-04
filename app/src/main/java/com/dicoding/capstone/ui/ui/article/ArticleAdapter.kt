package com.dicoding.capstone.ui.ui.article

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.capstone.R
import com.dicoding.capstone.response.DataItem

class ArticleAdapter : ListAdapter<DataItem, ArticleAdapter.ArticleViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }
        }
    }
    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.tv_title)
        private val description: TextView = itemView.findViewById(R.id.tv_description)
        private val image: ImageView = itemView.findViewById(R.id.img_article)

        fun bind(article: DataItem) {
            title.text = article.title
            description.text = article.content
            Glide.with(itemView.context)
                .load(article.imageUrl)
                .into(image)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ArticleDetailActivity::class.java).apply {
                    putExtra(ArticleDetailActivity.EXTRA_TITLE, article.title)
                    putExtra(ArticleDetailActivity.EXTRA_DESCRIPTION, article.content)
                    putExtra(ArticleDetailActivity.EXTRA_IMAGE_URL, article.imageUrl)
                }
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.article_layout_horizontal, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        if (article != null) {
            holder.bind(article)
        }
    }
}
