package com.example.animenews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animenews.databinding.ActivityMainBinding
import com.example.animenews.datos.entidades.AnimeItem
import com.example.animenews.utils.AnimeNews
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        lifecycleScope.launch(Dispatchers.Main) {
            delay(2000)
            val arrayItems = withContext(Dispatchers.IO) {
                val items = AnimeNews.getDbInstance().animeItem().getAllItems()
                if (items.isEmpty()) {
                    for (i in DataRepository.listAnime()) {
                        AnimeNews.getDbInstance().animeItem().insertItem(i)
                    }
                    val items = AnimeNews.getDbInstance().animeItem().getAllItems()
                }
                return@withContext items
            }

            val listRV = binding.listRV
            val adapterRv = NewsItemAdapter(arrayItems) { manageClickItem(it) }
            listRV.adapter = adapterRv
            listRV.layoutManager = LinearLayoutManager(
                baseContext,
                LinearLayoutManager.VERTICAL,
                false
            )
            binding.shimmerLayout.visibility = View.GONE
        }
    }

    private fun manageClickItem(item: AnimeItem) {
        //Toast.makeText(this, item.name, Toast.LENGTH_SHORT).show()
        val intent = Intent(this, ShowItemActivity::class.java)
        startActivity(intent)
    }
}