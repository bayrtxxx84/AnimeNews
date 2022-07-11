package com.example.animenews.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.animenews.R
import com.example.animenews.databinding.ActivityShowItemBinding

class ShowItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }



}