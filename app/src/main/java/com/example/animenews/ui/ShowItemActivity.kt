package com.example.animenews.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.animenews.R
import com.example.animenews.data.entidades.AnimeItem
import com.example.animenews.databinding.ActivityShowItemBinding
import com.squareup.picasso.Picasso
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class ShowItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowItemBinding
    private lateinit var item1: AnimeItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let {
            item1 = Json.decodeFromString(it.get("item").toString()) as AnimeItem
        }

        binding.btnMostrar.setOnClickListener {
            ShowItem()
        }
    }

    private fun ShowItem() {
        Picasso.get().load(item1.photo).into(binding.itemPhoto)
        binding.itemTitle.text = item1.name.toString()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        var intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent)
    }

}