package com.ahmadalfan.movieapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ahmadalfan.movieapp.R
import com.ahmadalfan.movieapp.databinding.ActivityDetailBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class DetailActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()
    private lateinit var binding: ActivityDetailBinding
    private var id: Int? = null
    private var voteaverage: Int? = null
    private var title: String? = null
    private var desc: String? = null
    private var originallanguage: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        id = intent.getIntExtra("id", 0)
        title = intent.getStringExtra("title")
        desc = intent.getStringExtra("overview")
        originallanguage = intent.getStringExtra("original_language")

        binding.tvTitle.text = title
        binding.tvDesc.text = desc
        binding.tvOriginal.text = originallanguage

        println("id : $id")
        println("title : $title")
        println("desc : $desc")

    }
}