package com.example.animenews.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animenews.data.entidades.AnimeItem
import com.example.animenews.data.repositories.DataRepository
import com.example.animenews.databinding.FragmentListItemsBinding
import com.example.animenews.ui.activities.ShowItemActivity
import com.example.animenews.ui.adapters.NewsItemAdapter
import com.example.animenews.utils.AnimeNews
import com.example.animenews.utils.FunctionsFragments
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class ListItemsFragment : Fragment() {

    private lateinit var binding: FragmentListItemsBinding
    private val adapterRv = NewsItemAdapter() { manageClickItem(it) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListItemsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFragment()
    }

    private fun initFragment() {

        lifecycleScope.launch(Dispatchers.Main) {
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

            adapterRv.dataList = listItems
            binding.listRV.adapter = adapterRv
            binding.listRV.layoutManager = LinearLayoutManager(
                activity?.baseContext,
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }

    private fun manageClickItem(item: AnimeItem) {
        val intent = Intent(activity?.baseContext, ShowItemActivity::class.java)
        val itemJson = Json.encodeToString(item)
        intent.putExtra("item", itemJson)
        startActivity(intent)
    }

}