package com.example.animenews.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        ShowInitItem()

        binding.btnCompartir.setOnClickListener {
            ShareItem()
        }
    }

    private fun ShareItem() {

        /*
        Intent explicit
          var intent = Intent(this, LoginActivity::class.java)
          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
          intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          startActivity(intent)

         Intent View (implicit)
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://www.tutorialkart.com/")
            }
            startActivity(intent)

         Intent Share (implicit)
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, item1.toString())
                type = "text/plain"
            }
            startActivity(intent)
         */

        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, item1.name.toString())
            type = "text/plain"
        }
        startActivity(sendIntent)
    }

    private fun ShowInitItem() {
        Picasso.get().load(item1.photo).into(binding.itemPhoto)
        binding.itemTitle.text = item1.name
    }
}