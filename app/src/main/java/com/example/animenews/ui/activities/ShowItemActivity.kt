package com.example.animenews.ui.activities


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.preference.PreferenceManager
import com.example.animenews.data.entidades.AnimeItem
import com.example.animenews.databinding.ActivityShowItemBinding
import com.squareup.picasso.Picasso
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.osmdroid.config.Configuration
import java.util.*

class ShowItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowItemBinding
    private lateinit var item1: AnimeItem

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let {
            item1 = Json.decodeFromString(it.get("item").toString()) as AnimeItem
        }

        ShowInitItem()

        val micResp =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    val message =
                        it.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0)
                    binding.itemTitle.text = message

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

        val respuesta =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    val message = it.data?.getStringExtra("val")
                    binding.itemTitle.text = message
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
            }

        binding.btnCompartir.setOnClickListener {
            // ShareItem()
            // SharePreferences()

            //ActivityForResult
            respuesta.launch(Intent(this, ResultActivity::class.java))
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

         Intent Share (implicit)*/
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, item1.name)
            type = "text/plain"
        }
        startActivity(sendIntent)
    }

    private fun ShowInitItem() {
        Picasso.get().load(item1.photo).into(binding.itemPhoto)
        binding.itemTitle.text = item1.name
    }

    fun SharePreferences() {
        val publicShare =
            applicationContext.getSharedPreferences(
                "sesiones",
                Context.MODE_PRIVATE
            )

        with(publicShare.edit()) {
            putString("nombre", "Alex")
            putString("apellido", "Bustamante")
        }.apply()

        val activityShare = PreferenceManager.getDefaultSharedPreferences(this)
        with(activityShare.edit()) {
            putString("nombre", "Bayron")
            putString("apellido", "Torres")
        }.apply()

        val a = publicShare.getString("nombre", "Dato inexistente")
        val b = activityShare.getString("nombre", "Dato inexistente")

        Log.d("UCE", a.toString())
        Log.d("UCE", b.toString())
    }

}