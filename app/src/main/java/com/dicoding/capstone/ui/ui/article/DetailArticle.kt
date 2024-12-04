package com.dicoding.capstone.ui.ui.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.dicoding.capstone.databinding.FragmentDetailArticleBinding

class DetailArticle : Fragment() {

    private lateinit var binding: FragmentDetailArticleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailArticleBinding.inflate(inflater, container, false)

        // Ambil data dari arguments
        val title = arguments?.getString(EXTRA_TITLE)
        val description = arguments?.getString(EXTRA_DESCRIPTION)
        val imageUrl = arguments?.getString(EXTRA_IMAGE)

        // Set data ke view
        binding.tvDetailTitle.text = title
        binding.tvDetailDescription.text = description
        // Gunakan Glide untuk memuat gambar
        Glide.with(this)
            .load(imageUrl)
            .into(binding.imgDetail)

        return binding.root
    }

    companion object {
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_DESCRIPTION = "extra_description"
        const val EXTRA_IMAGE = "extra_image"

        // Factory method untuk membuat instance dari fragment dengan data
        fun newInstance(title: String, description: String, imageUrl: String): DetailArticle {
            val fragment = DetailArticle()
            val args = Bundle().apply {
                putString(EXTRA_TITLE, title)
                putString(EXTRA_DESCRIPTION, description)
                putString(EXTRA_IMAGE, imageUrl)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
