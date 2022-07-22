package com.example.animenews.ui.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.animenews.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener{

            val data =  Intent()
            data.putExtra("val", "Bienvenido usuario")

            setResult(Activity.RESULT_OK, data)
            finish()
        }

    }
}