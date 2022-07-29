package com.example.animenews.ui.activities


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.animenews.data.entidades.database.AnimeItem
import com.example.animenews.databinding.ActivityShowItemBinding
import com.example.animenews.model.entidades.AnimeItemModel
import com.example.animenews.model.usercase.AnimeItemsUserCase
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.util.*

class ShowItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowItemBinding
    private lateinit var newItem: AnimeItemModel

    private val micResp =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val message =
                    it.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0)

                if (!message.isNullOrEmpty()) {
                    val searchIntent = Intent(Intent.ACTION_WEB_SEARCH)
                    searchIntent.setClassName(
                        "com.google.android.googlequicksearchbox",
                        "com.google.android.googlequicksearchbox.SearchActivity"
                    )
                    searchIntent.putExtra("query", message)
                    startActivity(searchIntent)
                }
            }
        }

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowItemBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var idItem = 0
        intent.extras?.let {
            val item = Json.decodeFromString(it.get("item").toString()) as AnimeItem
            idItem = item.id
        }
        showInitItem(idItem)

        binding.btnMic.setOnClickListener {
            val speak = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            speak.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            speak.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault()
            )
            speak.putExtra(RecognizerIntent.EXTRA_PROMPT, "Di algo...")
            micResp.launch(speak)
        }


        binding.btnCompartir.setOnClickListener {
            ShareItem()
        }
    }


    private fun ShareItem() {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, newItem.url)
            type = "text/plain"
        }
        startActivity(sendIntent)
    }

    private fun showInitItem(idItem: Int) {

        lifecycleScope.launch(Dispatchers.Main) {

            binding.shimmerItem.root.visibility = View.VISIBLE
            binding.container.visibility = View.GONE

            newItem = withContext(Dispatchers.IO) {
                AnimeItemsUserCase().getFullAnimeItem(idItem)
            }

            binding.shimmerItem.root.apply {
                alpha = 1f
                visibility = View.GONE
                animate()
                    .alpha(0f).duration = 1000L
            }

            binding.container.apply {
                alpha = 0f
                visibility = View.VISIBLE
                animate()
                    .alpha(1f).duration = 100L
            }

            Picasso.get().load(newItem.images?.jpg?.image_url).into(binding.itemPhoto)
            binding.itemTitle.text = newItem.title
            binding.itemUrl.text = newItem.url
            binding.itemDesc.text = newItem.synopsis
        }
    }
}