package com.example.animenews.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animenews.data.entidades.database.AnimeItem
import com.example.animenews.databinding.FragmentListItemsBinding
import com.example.animenews.model.usercase.AnimeItemsUserCase
import com.example.animenews.ui.activities.ShowItemActivity
import com.example.animenews.ui.adapters.NewsItemAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class ListItemsFragment : Fragment() {

    private var page: Int = 0

    private lateinit var binding: FragmentListItemsBinding
    private val adapterRv = NewsItemAdapter { manageClickItem(it) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListItemsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Listener que permite el scroll en el recyclerView
        binding.listRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    page += 1
                    val listItems: MutableList<AnimeItem> =
                        adapterRv.dataList as MutableList<AnimeItem>
                    lifecycleScope.launch(Dispatchers.Main) {
                        val newItems = withContext(Dispatchers.IO) {
                            AnimeItemsUserCase().getAllAnimeItems(page)
                        }
                        listItems.addAll(newItems)
                        adapterRv.dataList = listItems
                        adapterRv.notifyDataSetChanged()
                    }

                }
            }
        })

        initFragment()
    }

    private fun initFragment() {

        lifecycleScope.launch(Dispatchers.Main) {
            binding.shimmerLayout.root.visibility = View.VISIBLE
            val listItems = withContext(Dispatchers.IO) {
                AnimeItemsUserCase().getAllAnimeItems(page)
            }
            adapterRv.dataList = listItems
            binding.listRV.adapter = adapterRv
            binding.listRV.layoutManager = LinearLayoutManager(
                activity?.baseContext,
                LinearLayoutManager.VERTICAL,
                false
            )
            binding.shimmerLayout.root.visibility = View.GONE
        }
    }

    private fun manageClickItem(item: AnimeItem) {
        val intent = Intent(activity?.baseContext, ShowItemActivity::class.java)
        val itemJson = Json.encodeToString(item)
        intent.putExtra("item", itemJson)
        startActivity(intent)
    }

}