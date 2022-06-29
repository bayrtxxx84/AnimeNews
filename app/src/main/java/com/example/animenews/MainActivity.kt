package com.example.animenews

import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animenews.databinding.ActivityMainBinding
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch(Dispatchers.Main) {
            delay(5000)
            var arrayItems = withContext(Dispatchers.IO) { APICall().returnItems() }
            var listRV = binding.listRV
            val adapterRv = NewsItemAdapter(arrayItems) {
                manageItemClick(it)
            }
            listRV.adapter = adapterRv
            listRV.layoutManager = LinearLayoutManager(
                baseContext,
                LinearLayoutManager.VERTICAL,
                false
            )
            binding.shimerRv.visibility = View.GONE
        }

        ///////////////////////////////////////////////////////////////////////////////////////////
        // Funciones lambda
        fun suma(a: Int, b: Int): Int {
            return a + b
        }

        val suma = { a: Int, b: Int -> a + b }
        fun calc(a: Int, b: Int, myfun: (a: Int, b: Int) -> Int) {
            myfun(a, b)
        }
        calc(4, 5) { a: Int, b: Int -> a + b }
    }

    fun manageItemClick(item: ItemDataClass) {
        Toast.makeText(this, "El item que has seleccionado es: ${item.name}", Toast.LENGTH_SHORT)
            .show()
    }

}