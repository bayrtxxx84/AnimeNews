package com.example.animenews.ui.activities

import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.animenews.R
import com.example.animenews.data.entidades.AnimeItem
import com.example.animenews.data.repositories.DataRepository
import com.example.animenews.databinding.ActivityMainBinding
import com.example.animenews.ui.fragments.ListItemsRVFragment
import com.example.animenews.ui.fragments.ShimmerFragment
import com.example.animenews.utils.AnimeNews
import com.example.animenews.utils.FunctionsFragments
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val fragmFun = FunctionsFragments(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        lifecycleScope.launch(Dispatchers.Main) {

            fragmFun.ReplaceShimmerFragment(ShimmerFragment(), binding.fragmentView.id)

            delay(2000)
            val listItems = withContext(Dispatchers.IO) {
                var items: List<AnimeItem>
                items = AnimeNews.getDbInstance().animeItem().getAllItems()
                if (items.isEmpty()) {
                    for (i in DataRepository.listAnime()) {
                        AnimeNews.getDbInstance().animeItem().insertItem(i)
                    }
                    items = AnimeNews.getDbInstance().animeItem().getAllItems()
                }
                return@withContext items
            }

            val bundle = Bundle()
            bundle.putSerializable("item", Json.encodeToString(listItems))
            fragmFun.ReplaceFragment(
                ListItemsRVFragment(), binding.fragmentView.id,
                listOf(bundle), null
            )

            fragmFun.RemoveFragment("shimmer")

        }
    }
}

