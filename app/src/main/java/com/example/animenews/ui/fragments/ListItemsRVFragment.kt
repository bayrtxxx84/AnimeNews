package com.example.animenews.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animenews.data.entidades.AnimeItem
import com.example.animenews.databinding.FragmentListItemsRvBinding
import com.example.animenews.ui.activities.ShowItemActivity
import com.example.animenews.ui.adapters.NewsItemAdapter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class ListItemsRVFragment : Fragment() {

    private lateinit var binding: FragmentListItemsRvBinding
    private val adapterRv = NewsItemAdapter() { manageClickItem(it) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListItemsRvBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { itemList ->
            adapterRv.dataList =
                Json.decodeFromString<List<AnimeItem>>(itemList.getSerializable("item").toString())
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