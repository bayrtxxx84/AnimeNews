package com.example.animenews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animenews.databinding.ActivityMainBinding
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val array = listOf<String>(
            "Api 1", "Api 2", "Api 3", "Api 4", "Api 5", "Api 6", "Api 7", "Api 8", "Api 9",
            "Api 11", "Api 12", "Api 13", "Api 14", "Api 15", "Api 16", "Api 17", "Api 18"
        )
        var adapter: ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            array
        )

        val arrayItems = listOf<ItemDataClass>(
            ItemDataClass(1, R.drawable.arturos, "Arturos"),
            ItemDataClass(2, R.drawable.foodpanda, "FootPanda"),
            ItemDataClass(3, R.drawable.swiggy, "Swiggy"),
            ItemDataClass(4, R.drawable.uber, "Uber"),
            ItemDataClass(5, R.drawable.yelp, "Yelp"),
            ItemDataClass(6, R.drawable.zomato, "Zomato")
        )

        var listRV = binding.listRV
        val adapterRv = NewsItemAdapter(arrayItems) { manageClickItem(it) }
        listRV.adapter = adapterRv
        listRV.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.shimmerLayout.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
    }

    private fun manageClickItem(item: ItemDataClass) {
        Toast.makeText(this, item.name, Toast.LENGTH_SHORT).show()
    }

    fun suma(a: Int, b: Int): Int {
        return a + b
    }

    val suma = { a: Int, b: Int -> a + b }

    fun calc(a: Int, b: Int, myFun: (Int, Int) -> Int): Int {
        return myFun(a, b)
    }

}