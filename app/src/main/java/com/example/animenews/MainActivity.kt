package com.example.animenews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animenews.databinding.ActivityMainBinding
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
                DataRepository.listAnime()

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
        Toast.makeText(this, item.name, Toast.LENGTH_SHORT).show()
    }

}