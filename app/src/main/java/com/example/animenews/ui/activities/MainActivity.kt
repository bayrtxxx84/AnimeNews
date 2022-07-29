package com.example.animenews.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.animenews.ui.fragments.OSMMapsFragment
import com.example.animenews.R
import com.example.animenews.databinding.ActivityMainBinding
import com.example.animenews.ui.fragments.ListItemsFragment
import com.example.animenews.ui.fragments.MapBoxFragment
import com.example.animenews.utils.FunctionsFragments

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val fragmFun = FunctionsFragments(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.news -> {
                    initActivity()
                    true
                }

                R.id.osmMap -> {
                    fragmFun.ReplaceFragment(
                        OSMMapsFragment(), binding.fragmentView.id,
                        null, null
                    )
                    true
                }

                R.id.mapBoxMap -> {
                    fragmFun.ReplaceFragment(
                        MapBoxFragment(), binding.fragmentView.id,
                        null, null
                    )
                    true
                }

                else -> false
            }
        }

        initActivity()
    }

    fun initActivity() {
        fragmFun.ReplaceFragment(
            ListItemsFragment(), binding.fragmentView.id,
            null, null
        )
    }

}
